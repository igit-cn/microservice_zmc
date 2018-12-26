package com.zmc.springcloud.service.impl;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.mapper.BusinessOrderMapper;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.OrderTransactionSNGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zmc.springcloud.utils.CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_REVIEW;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {
    @Autowired
    private BusinessOrderMapper businessOrderMapper;

    @Autowired
    private SpecialtySpecificationService specialtySpecificationService;

    @Autowired
    private HyGroupitemPromotionService hyGroupitemPromotionService;

    @Autowired
    private HySingleitemPromotionService hySingleitemPromotionService;

    @Autowired
    private WechatAccountService wechatAccountService;

    @Autowired
    private CommonSequenceService commonSequenceService;

    @Autowired
    private CouponGiftService couponGiftService;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private BusinessOrderItemService businessOrderItemService;

    @Autowired
    private OrderTransactionService orderTransactionService;

    @Autowired
    private VipService vipService;

    @Autowired
    private PointRecordService pointRecordService;

    @Autowired
    private CouponBalanceUseService couponBalanceUseService;

    @Override
    public Map<String, Object> createOrder(HashMap<String, Object> params, HashMap<String, Object> bodys) throws Exception{
        Map<String, Object> ret = new HashMap<>();
        HashMap<String, Object> customOrder = null;
        if (params != null) {
            customOrder = params;
        }
        if (bodys != null) {
            customOrder = bodys;
        }
        if (customOrder == null) {
          throw new Exception("没有请求参数");
        }

        BusinessOrder businessOrder = new BusinessOrder();
        businessOrder.setOrderPhone((String) customOrder.get("orderPhone"));
        businessOrder.setTotalMoney(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("totalMoney"))));
        businessOrder.setPromotionAmount(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("promotionAmount"))));
        businessOrder.setShipFee(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("shipFee"))));
        businessOrder.setShouldpayMoney(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("shouldPayMoney"))));
        businessOrder.setCouponMoney(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("couponMoney"))));
        businessOrder.setBalanceMoney(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("balanceMoney"))));
        businessOrder.setPayMoney(BigDecimal.valueOf(Double.parseDouble((String) customOrder.get("payMoney"))));
        businessOrder.setReceiverRemark((String) customOrder.get("receiverRmark"));
        businessOrder.setReceiverAddress((String) customOrder.get("receiverAddress"));
        businessOrder.setReceiverName((String) customOrder.get("receiverName"));
        businessOrder.setReceiverPhone((String) customOrder.get("receiverPhone"));
        businessOrder.setReceiveType((Integer) customOrder.get("receiverType"));
        Long wechatId = customOrder.get("orderWechatId") == null ? null : ((Integer) customOrder.get("orderWechatId")).longValue();
        Long webusinessId = customOrder.get("webusinessId") == null ? null : ((Integer) customOrder.get("webusinessId")).longValue();

        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) customOrder.get("orderItems");
        List<Integer> coupons = (List<Integer>) customOrder.get("coupons");

        /* 判断库存 */
        if(!specialtySpecificationService.isBaseInboundEnough(orderItems)){
            throw new Exception("库存不足，无法下单");
        }
        /* 判断优惠活动数量是否满足 */
        for (Map<String, Object> orderItem : orderItems){
            Boolean isGroupPromotion = (Boolean) orderItem.get("isGroupPromotion");
            // 如果是组合产品
            if (isGroupPromotion) {
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionService.find(((Integer) orderItem.get("specialtyId")).longValue());
                // 获取购买数量
                Integer quantity = (Integer) orderItem.get("quantity");
                //判断优惠数量
                if(groupitemPromotion.getPromoteNum()<quantity) {
                    throw new Exception("组合优惠活动数量不足，无法下单");
                }
                if(groupitemPromotion.getLimitedNum()<quantity) {
                    throw new Exception("超出组合优惠限购数量");
                }
            }else{
                // 如果是普通产品
                if(orderItem.get("promotionId") != null) {
                    Long promotionId = ((Integer) orderItem.get("promotionId")).longValue();
                    Integer quantity = (Integer) orderItem.get("quantity");
                    // 获取优惠明细
                    HySingleitemPromotion singleitemPromotion = hySingleitemPromotionService.getValidSingleitemPromotion(((Integer) orderItem.get("specialtySpecificationId")).longValue(), promotionId);
                    // 如果参加了优惠活动
                    if (singleitemPromotion != null) {
                        //判断优惠活动数量
                        if(singleitemPromotion.getPromoteNum()<quantity) {
                            throw new Exception("普通优惠活动数量不足，无法下单");
                        }
                        if(singleitemPromotion.getLimitedNum()<quantity) {
                            throw new Exception("超出普通优惠限购数量");
                        }
                    }
                }
            }
        }
        /* 判断余额*/
        if (wechatId == null) {
            throw new Exception("微信账号不存在，无法下单");
        }
        WechatAccount wechatAccount = wechatAccountService.findById(wechatId);
        // 获取使用余额
        BigDecimal balanceMoney = businessOrder.getBalanceMoney();
        if(wechatAccount.getTotalbalance().compareTo(balanceMoney)<0) {
            throw new Exception("账户余额不足，无法下单");
        }
        /*
		* 购买数量没问题，进行下列步骤 2-建立订单记录
		*/
        // 得到下单微信账户
        businessOrder.setOrderWechatId(wechatId);

        // 得到订单所属微商
        if (webusinessId == null) {
            businessOrder.setWeBusinessId(null);
        } else {
            businessOrder.setWeBusinessId(webusinessId);
        }
        // 设置订单状态  0待付款
        businessOrder.setOrderState(0);
        // 设置下单时间
        businessOrder.setOrderTime(new Date());
        // 设置电子券id字符串
        String couponsStr = "";
        for (Integer coupon : coupons) {
            couponsStr += String.format("%d", coupon) + ";";
        }
        businessOrder.setCouponId(couponsStr);
        String code = this.getOrderCode();
        businessOrder.setOrderCode(code);

        // 在原工程中使用@PrePersist进行处理
        businessOrder.setIsValid(true);
        businessOrder.setIsShow(false);
        businessOrder.setIsAppraised(false);
        businessOrder.setIsDivided(false);
        businessOrder.setIsBalance(false);
        businessOrder.setIsBalanced(false);

        /*
		* 3-修改优惠数量
		*/
        for (Map<String, Object> orderItem : orderItems) {
            Boolean isGroupPromotion = (Boolean) orderItem.get("isGroupPromotion");
            // 如果是组合产品
            if(isGroupPromotion){
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionService.find(((Integer) orderItem.get("specialtyId")).longValue());
                // 获取购买数量
                Integer quantity = (Integer) orderItem.get("quantity");
                // 修改优惠活动数量
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum() - quantity);
                groupitemPromotion.setHavePromoted(groupitemPromotion.getHavePromoted() + quantity);
                hyGroupitemPromotionService.updatePromotion(groupitemPromotion);
            }else {
                // 如果是普通产品
                // 获取购买数量
                if(orderItem.get("promotionId") != null) {
                    Long promotionId = ((Integer) orderItem.get("promotionId")).longValue();
                    Integer quantity = (Integer) orderItem.get("quantity");
                    // 获取优惠明细
                    HySingleitemPromotion singleitemPromotion = hySingleitemPromotionService.getValidSingleitemPromotion(((Integer) orderItem.get("specialtySpecificationId")).longValue(), promotionId);
                    // 如果参加了优惠活动
                    if (singleitemPromotion != null) {
                        // 修改优惠数量
                        singleitemPromotion.setPromoteNum(singleitemPromotion.getPromoteNum() - quantity);
                        singleitemPromotion.setHavePromoted(singleitemPromotion.getHavePromoted() + quantity);
                        hySingleitemPromotionService.updatePromotion(singleitemPromotion);
                    }
                }
            }
        }

        /*
         * 4-修改余额电子券
	     */
        // 修改账户余额
        if (balanceMoney.compareTo(BigDecimal.ZERO) > 0) {
            wechatAccount.setTotalbalance(wechatAccount.getTotalbalance().subtract(balanceMoney));
            wechatAccountService.updateTotalBalance(wechatAccount);
        }
        for (Integer couponId : coupons) {
            CouponGift coupon = couponGiftService.find(couponId.longValue());
            if(coupon != null){
                // 设置为已使用 1 已使用
                coupon.setState(1);
                // 设置使用时间
                coupon.setUseTime(new Date());
                couponGiftService.update(coupon);
            }
        }
        // 将订单信息保存至数据库中


        businessOrderMapper.save(businessOrder);

        // 设置订单条目
        // 由于没有使用级联 此处和原项目不同 需要在保存BusinessOrder之后再保存BusinessOrderItem
        for (Map<String, Object> orderItem : orderItems) {
            BusinessOrderItem businessOrderItem = new BusinessOrderItem();
            // 设置订单
            businessOrderItem.setOrderId(businessOrder.getId());
            // 设置产品
            businessOrderItem.setSpecialtyId(((Integer) orderItem.get("specialtyId")).longValue());
            // 设置规格
            businessOrderItem.setSpecialtySpecificationId(((Boolean) orderItem.get("isGroupPromotion")) ? null : ((Integer) orderItem.get("specialtySpecificationId")).longValue());
            businessOrderItem.setQuantity((Integer) orderItem.get("quantity"));
            BigDecimal curPrice = (BigDecimal.valueOf(Double.parseDouble((String) orderItem.get("curPrice"))));
            BigDecimal promotionPrice = curPrice.multiply(businessOrder.getPromotionAmount()
                    .divide(businessOrder.getTotalMoney(), 3, BigDecimal.ROUND_HALF_DOWN));
            // 设置购买原价
            businessOrderItem.setOriginalPrice(curPrice);
            // 设置优惠后价格
            businessOrderItem.setSalePrice(curPrice.subtract(promotionPrice));
            businessOrderItem.setIsappraised(false);
            // 设置优惠活动
            if (orderItem.get("promotionId") != null) {
                businessOrderItem.setPromotionId(((Integer) orderItem.get("promotionId")).longValue());
            } else {
                businessOrderItem.setPromotionId(null);
            }
            // 0:普通产品；1：组合产品
            businessOrderItem.setType(((Boolean) orderItem.get("isGroupPromotion")) ? 1 : 0);

            businessOrderItem.setIsGift((Boolean)orderItem.get("isGift"));
            // 设置供应商
            if (businessOrderItem.getType() == 1) {
                // 如果是组合产品
                businessOrderItem.setDeliverType(0);
            } else {
                // 如果是普通产品
                Specialty specialty = specialtyService.find(businessOrderItem.getSpecialtyId());
                Provider provider = providerService.find(specialty.getProviderId());
                businessOrderItem.setDeliverName(provider.getProviderName());
            }
            businessOrderItemService.save(businessOrderItem);

            //更新规格的基础库存和销量
            specialtySpecificationService.updateBaseInboundAndHasSold(businessOrderItem, true);
        }


        ret.put("orderId", businessOrder.getId());
        ret.put("orderCode", businessOrder.getOrderCode());
        return ret;
    }

    @Override
    public void updateOrderAfterPay(String orderCode)throws Exception {
        BusinessOrder businessOrder = businessOrderMapper.getByOrderCode(orderCode);
        if(businessOrder != null){
            businessOrder.setOrderState(BUSINESS_ORDER_STATUS_WAIT_FOR_REVIEW);
            businessOrder.setPayTime(new Date());
            businessOrderMapper.updateOrderStateAndPayTime(businessOrder);

            // 添加交易记录表 生成交易记录
            OrderTransaction transaction = new OrderTransaction();
            transaction.setOrderId(businessOrder.getId());
            transaction.setSerialNum(OrderTransactionSNGenerator.getSN(true));
            transaction.setWechatBalance(businessOrder.getBalanceMoney());
            transaction.setOrderCoupon(businessOrder.getCouponMoney());
            WechatAccount wechatAccount = wechatAccountService.findById(businessOrder.getOrderWechatId());
            transaction.setPayAccount(wechatAccount.getWechatOpenid());
            // 微信支付
            transaction.setPayType(1);
            transaction.setPayment(businessOrder.getPayMoney());
            transaction.setPayFlow(1);
            transaction.setPayTime(businessOrder.getPayTime());
            orderTransactionService.save(transaction);

            // 发送短信
//            StringBuilder sb1 = new StringBuilder();
//            for (BusinessOrderItem item : businessOrder.getBusinessOrderItems()) {
//                sb1.append(businessOrderItemService.getSpecialtyName(item) + "("
//                        + businessOrderItemService.getSpecificationName(item) + "*" + item.getQuantity() + ")；");
//            }
//            String phone = null;
//            if (businessOrder.getOrderPhone() != null) {
//                phone = businessOrder.getOrderPhone();
//            } else {
//                phone = businessOrder.getReceiverPhone();
//            }
//            if (phone != null) {
//                //write by wj
//                String amount = businessOrder.getPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP) + "元";
//                String code = businessOrder.getOrderCode();
//                String product = sb1.toString();
//                String message = "{\"amount\":\""+amount+"\",\"code\":\""+code+"\",\"product\":\""+product+"\"}";
//                SendMessageEMY.businessSendMessage(phone,message,5);
//            }

            //支付成功，修改用户积分
            //首先判断订单是否参加优惠活动
            if(havePromotions(businessOrder)){
                // 没有参加过优惠活动
                if(businessOrder.getShouldpayMoney().equals(businessOrder.getPayMoney())){
                    // 如果本订单的全部用现金支付
                    BigDecimal money = businessOrder.getPayMoney();
                    Integer changevalue = money.intValue()/10;
                    // 判断是否318会员
                    vipService.setVip318(wechatAccount, money);

                    if(changevalue != 0){
                        pointRecordService.changeUserPoint(businessOrder.getOrderWechatId(),changevalue , "购物");
                    }
                    // 用户首单奖励
                    if(wechatAccount.getIsNew()){
                        BigDecimal awardMoney = money.multiply(BigDecimal.valueOf(0.2));
                        if(awardMoney.doubleValue()>50) {
                            awardMoney = BigDecimal.valueOf(50);
                        }
                        //修改用户余额
                        if(wechatAccount.getTotalbalance()==null) {
                            wechatAccount.setTotalbalance(BigDecimal.ZERO);
                        }
                        wechatAccount.setTotalbalance(wechatAccount.getTotalbalance().add(awardMoney));
                        wechatAccount.setIsNew(false);
                        wechatAccountService.updateVipPointTotalpointTotalbalance(wechatAccount);

                        // 添加余额兑换记录
                        CouponBalanceUse couponBalanceUse = new CouponBalanceUse();
                        couponBalanceUse.setPhone(wechatAccount.getPhone());
                        // 6首单奖励
                        couponBalanceUse.setType(6);
                        couponBalanceUse.setState(1);
                        couponBalanceUse.setUseAmount(awardMoney.floatValue());
                        couponBalanceUse.setUseTime(new Date());
                        couponBalanceUse.setWechatId(wechatAccount.getId());
                        couponBalanceUseService.save(couponBalanceUse);
                    }
                }
            }
            //下单成功，修改用户是否为新用户
             if(wechatAccount.getIsNew()){
                wechatAccount.setIsNew(false);
                wechatAccountService.updateIsNew(wechatAccount);
             }
        }
    }

    public String getOrderCode() throws Exception{
        // 获取序列号
        Long value = commonSequenceService.getValue(CommonSequence.SequenceTypeEnum.businessOrderSuq) + 1;
        // 更新序列号
        commonSequenceService.updateValue(CommonSequence.SequenceTypeEnum.businessOrderSuq, value);
        // 生成订单编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowaday = sdf.format(new Date());
        // SN至少为8位,不足补零
        String code = nowaday + String.format("%08d", value);
        return code;
    }

    /** 判断是否有优惠活动*/
    public Boolean havePromotions(BusinessOrder order)throws Exception {
        List<BusinessOrderItem> items = businessOrderItemService.getListByOrderId(order.getId());
        for(BusinessOrderItem item:items) {
            if(item.getPromotionId()!=null) {
                return true;
            }
        }
        return false;
    }
}
