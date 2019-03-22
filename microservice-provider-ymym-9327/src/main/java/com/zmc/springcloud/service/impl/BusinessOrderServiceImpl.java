package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.product.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyImageFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionPicFeignClient;
import com.zmc.springcloud.feignclient.promotion.HySingleitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.mapper.BusinessOrderMapper;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.zmc.springcloud.utils.CommonAttributes.*;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {
    @Autowired
    private BusinessOrderMapper businessOrderMapper;

    @Autowired
    private BusinessOrderItemService businessOrderItemService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private SpecialtyFeignClient specialtyFeignClient;

    @Autowired
    private SpecialtyImageFeignClient specialtyImageFeignClient;

    @Autowired
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    @Autowired
    private HyGroupitemPromotionFeignClient hyGroupitemPromotionFeignClient;

    @Autowired
    private HyPromotionFeignClient hyPromotionFeignClient;

    @Autowired
    private HyPromotionPicFeignClient hyPromotionPicFeignClient;

    @Autowired
    private HySingleitemPromotionFeignClient hySingleitemPromotionFeignClient;

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private ShipService shipService;

    @Autowired
    private BusinessOrderRefundService businessOrderRefundService;

    @Override
    public HashMap<String, Object> getOrderListByAccount(Integer page, Integer rows, Long wechatId, Integer status, Boolean isValid, Boolean isShow) throws Exception {
        PageHelper.startPage(page, rows);
        List<BusinessOrder> orderList = businessOrderMapper.getOrderListByAccount(wechatId, status, isValid, isShow);
        PageInfo pageInfo = new PageInfo(orderList);
        HashMap<String, Object> obj = new HashMap<>();
        List<HashMap<String, Object>> orders = new LinkedList<>();
        for (BusinessOrder bOrder : orderList) {
            HashMap<String, Object> map = getOrderListItemMap(bOrder);
            orders.add(map);
        }
        obj.put("rows", orders);
        obj.put("pageNumber", page);
        obj.put("pageSize", rows);
        obj.put("total", pageInfo.getTotal());
        obj.put("totalPages", pageInfo.getPages());
        return obj;
    }

    @Override
    public HashMap<String, Object> getOrderDetailById(Long orderId) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        BusinessOrder order = businessOrderMapper.findById(orderId);
        map.put("baseInfo", order);
        // 订单条目
        List<BusinessOrderItem> itemList = businessOrderItemService.getBusinessOrderItemListByOrderId(orderId);
        List<HashMap<String, Object>> items = new LinkedList<>();
        for (BusinessOrderItem bItem : itemList) {
            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", bItem.getId());
            itemMap.put("specialtyId", bItem.getSpecialtyId());
            itemMap.put("specialtySpecificationId", bItem.getSpecialtySpecificationId());
            PurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(bItem.getPurchaseItemId());
            itemMap.put("purchaseItemId", purchaseItem);
            itemMap.put("quantity", bItem.getQuantity());
            itemMap.put("originalPrice", bItem.getOriginalPrice());
            itemMap.put("salePrice", bItem.getSalePrice());
            itemMap.put("isGift", bItem.getIsGift());
            int itemType = bItem.getType();
            // 普通商品
            if (itemType == 0) {
                Specialty specialty = specialtyFeignClient.getSpecialtyById(bItem.getSpecialtyId());
                itemMap.put("name", specialty.getName());
                List<SpecialtyImage> images = specialtyImageFeignClient.getSpecialtyImageListBySpecialtyId(bItem.getSpecialtyId());
                for (SpecialtyImage image : images) {
                    if (image.getIsLogo()) {
                        itemMap.put("iconURL", image);
                        break;
                    }
                }
                SpecialtySpecification specialtySpecification = specialtySpecificationFeignClient.getSpecialtySpecificationById(bItem.getSpecialtySpecificationId());
                itemMap.put("specification", specialtySpecification.getSpecification());
            } else {
                HyGroupitemPromotion hyGroupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(bItem.getSpecialtyId());
                HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(hyGroupitemPromotion.getPromotionId());
                itemMap.put("name", hyPromotion.getPromotionName());
                List<HyPromotionPic> images = hyPromotionPicFeignClient.getHyPromotionPicListByPromotionId(hyPromotion.getId());
                for (HyPromotionPic image : images) {
                    if (image.getIsTag()) {
                        itemMap.put("iconURL", image);
                    }
                }
                itemMap.put("specification", null);
            }
            itemMap.put("type", itemType);
            itemMap.put("deliverName", bItem.getDeliverName());
            itemMap.put("deliverType", bItem.getDeliverType());
            itemMap.put("depotName", bItem.getDepotName());

            items.add(itemMap);
        }
        // 订单条目
        map.put("orderItems", items);

        // 物流信息
        List<Ship> ships = shipService.getShipListByOrderId(orderId);
        map.put("ships", ships);

        return map;
    }

    @Override
    public Json getRefundOrderDetail(Long orderId) throws Exception {
        Json j = new Json();
        // 根据订单id获取退款订单
        BusinessOrder order = businessOrderMapper.findById(orderId);
        List<BusinessOrderRefund> orderRefunds = businessOrderRefundService.getListByBusinessOrderId(orderId);
        if (CollectionUtils.isEmpty(orderRefunds)) {
            j.setSuccess(false);
            j.setMsg("查询失败,不存在");
            return j;
        }

        BusinessOrderRefund orderRefund = orderRefunds.get(0);
        // 退款订单map
        Map<String, Object> orderRefundMap = new HashMap<>();
        // id
        orderRefundMap.put("id", orderRefund.getId());
        // 获取订单map
        HashMap<String, Object> orderMap = getOrderListItemMap(order);
        orderRefundMap.put("order", orderMap);
        // 状态
        orderRefundMap.put("state", orderRefund.getState());
        // 是否退货
        orderRefundMap.put("isDelivered", orderRefund.getIsDelivered());
        // 发货类型
        orderRefundMap.put("deliverType", orderRefund.getDeliverType());
        // 责任方
        orderRefundMap.put("responsibleParty", orderRefund.getResponsibleParty());
        // 应退款总额
        orderRefundMap.put("refundTotalAmount", orderRefund.getRefundTotalamount());
        // 退货物流公司
        orderRefundMap.put("refundShiper", orderRefund.getRefundShiper());
        // 退货物流单号
        orderRefundMap.put("refundShipCode", orderRefund.getRefundShipCode());
        // 退款理由
        orderRefundMap.put("refundReson", orderRefund.getRefundReason());
        // 获取退货明细map
        List<Map<String, Object>> refundItems = businessOrderItemService.getRefundItemMapList(order);
        orderRefundMap.put("refundItems", refundItems);

        j.setSuccess(true);
        j.setMsg("查询成功");
        j.setObj(orderRefundMap);

        return j;
    }

    @Override
    public Json applyRefund(HashMap<String, Object> params, HashMap<String, Object> bodys) throws Exception{
        Json j = new Json();
        HashMap<String, Object> refundInfo = null;
        if (params != null) {
            refundInfo = params;
        }
        if (bodys != null) {
            refundInfo = bodys;
        }
        // 订单ID
        Long orderId = ((Integer) refundInfo.get("orderId")).longValue();
        // 发货类型
        Integer deliverType = (Integer) refundInfo.get("deliverType");
        // 退款理由
        String refundReason = (String) refundInfo.get("refundReason");
        // 退货条目
        List<Map<String, Object>> refundItems = (List<Map<String, Object>>) refundInfo.get("refundItems");

        // BusinessOrderRefund Entity
        BusinessOrderRefund bRefund = new BusinessOrderRefund();
        BusinessOrder bOrder = businessOrderMapper.findById(orderId);
        if(bOrder == null || !bOrder.getOrderState().equals(BUSINESS_ORDER_STATUS_HAS_RECEIVED)){
            j.setSuccess(true);
            j.setMsg("订单不存在或状态不可申请退款退货");
            Map<String, Object> obj = new HashMap<>();
            obj.put("orderId", orderId);
            obj.put("orderState", bOrder.getOrderState());
            j.setObj(obj);
            return j;
        }
        // 订单
        bRefund.setBusinessOrderId(orderId);
        // 发货类型
        bRefund.setDeliverType(deliverType);
        // 退款理由
        bRefund.setRefundReason(refundReason);
        // 状态为售后人员待确认
        bRefund.setState(BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_CONFIRM);
        // 退款申请时间
        bRefund.setRefundApplyTime(new Date());
        // 下单微信账户
        bRefund.setWechatId(bOrder.getOrderWechatId());

        /* 退款金额以及设置退货条目 */
        // 订单优惠前总额
        BigDecimal orderTotal = bOrder.getTotalMoney().add(bOrder.getShipFee());
        // 余额支付金额
        BigDecimal orderBalance = bOrder.getBalanceMoney();
        // 其他支付金额
        BigDecimal orderPay = bOrder.getPayMoney();
        // 应退货商品总原价
        BigDecimal refundOriginal = BigDecimal.ZERO;
        // 遍历退货明细
        for (Map<String, Object> item : refundItems) {
            // 订单明细id
            Long itemId = ((Integer) item.get("id")).longValue();
            // 退货数量
            Integer refundQuantity = (Integer) item.get("refundQuantity");
            // 订单明细
            BusinessOrderItem orderItem = businessOrderItemService.getById(itemId);
            // 设置退货数量
            orderItem.setReturnQuantity(refundQuantity);
            refundOriginal = refundOriginal.add(orderItem.getOriginalPrice().multiply(BigDecimal.valueOf(refundQuantity)));

            businessOrderItemService.save(orderItem);

        }
        // 退款货物价格占比
        BigDecimal ratio = refundOriginal.divide(orderTotal, 8, BigDecimal.ROUND_HALF_DOWN);
        // 余额支付退款金额
        BigDecimal rRefundAmount = orderBalance.multiply(ratio);
        // 其他支付退款金额
        BigDecimal qRefundAmount = orderPay.multiply(ratio);
        // 货物退款金额
        BigDecimal refundAmount = rRefundAmount.add(qRefundAmount);
        // 设置余额支付退款金额
        bRefund.setRrefundAmount(rRefundAmount);
        // 设置其他支付退款金额
        bRefund.setQrefundAmount(qRefundAmount);
        // 设置货物退款金额
        bRefund.setRefundAmount(refundAmount);
        // 设置应退款总额
        bRefund.setRefundTotalamount(refundAmount);

        // 保存退款订单信息
        businessOrderRefundService.save(bRefund);

        // 订单设置退款金额
        bOrder.setRefoundMoney(refundAmount);
        // 设置订单为退款待确认状态
        bOrder.setOrderState(BUSINESS_ORDER_STATUS_APPLY_RETURN_GOODS_TO_CONFIRM);
        // 更新订单信息
        businessOrderMapper.updateRefundMoneyAndOrderState(bOrder);

        j.setSuccess(true);
        j.setMsg("操作成功");

        return j;
    }

    @Override
    public Json confirmReceive(Long id) throws Exception {
        Json j = new Json();
        BusinessOrder businessOrder = businessOrderMapper.findById(id);
        if(businessOrder == null){
            j.setSuccess(true);
            j.setMsg("订单不存在");
            return j;
        }
        int state = businessOrder.getOrderState();
        if(state == BUSINESS_ORDER_STATUS_WAIT_FOR_RECEIVE){
            // 如果是等待收货状态,则执行确认收货动作
            businessOrder.setOrderState(BUSINESS_ORDER_STATUS_HAS_RECEIVED);
            businessOrder.setReceiveTime(new Date());
            businessOrderMapper.updateReceiveTimeAndOrderState(businessOrder);

            j.setSuccess(true);
            j.setMsg("操作成功");
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", businessOrder.getId());
            obj.put("state", businessOrder.getOrderState());
            j.setObj(obj);
        } else {
            j.setSuccess(true);
            j.setMsg("订单状态错误，无法确认收货");
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", businessOrder.getId());
            obj.put("state", businessOrder.getOrderState());
            j.setObj(obj);
        }

        return j;
    }

    @Override
    public void deleteBusinessOrder(Long id) throws Exception {
        BusinessOrder order = businessOrderMapper.findById(id);
        if(order == null){
            throw new Exception("没有有效订单");
        }
        order.setIsValid(false);
        businessOrderMapper.updateIsValid(order);
    }

    @Override
    public Json cancelOrder(Long id) throws Exception {
        Json j = new Json();
        BusinessOrder businessOrder = businessOrderMapper.findById(id);
        if (businessOrder == null) {
            j.setSuccess(true);
            j.setMsg("订单不存在");
            return j;
        }
        int state = businessOrder.getOrderState();
        if (state < BUSINESS_ORDER_STATUS_WAIT_FOR_DELIVERY) {
            // 恢复规格商品的库存和销量
            List<BusinessOrderItem> orderItems = businessOrderItemService.getBusinessOrderItemListByOrderId(id);
            for (BusinessOrderItem orderItem : orderItems) {
                specialtySpecificationFeignClient.updateBaseInboundAndHasSold(orderItem, false);
                // 修改优惠数量
                Boolean isGroupPromotion = orderItem.getType() != 0;
                // 如果是组合产品
                if (isGroupPromotion) {
                    // 获取组合优惠活动对象
                    HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(orderItem.getSpecialtyId());
                    // 获取购买数量
                    Integer quantity = orderItem.getQuantity();
                    // 修改优惠活动数量
                    groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum() + quantity);
                    groupitemPromotion.setHavePromoted(groupitemPromotion.getHavePromoted() - quantity);
                    hyGroupitemPromotionFeignClient.updateGroupitemPromotion(groupitemPromotion);
                } else {
                    // 如果是普通产品
                    // 获取购买数量
                    if (orderItem.getPromotionId() != null) {
                        Integer quantity = orderItem.getQuantity();
                        // 获取优惠明细
                        HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(orderItem.getSpecialtySpecificationId(), orderItem.getPromotionId());
                        // 如果参加了优惠活动
                        if (singleitemPromotion != null) {
                            // 修改优惠数量
                            singleitemPromotion.setPromoteNum(singleitemPromotion.getPromoteNum() + quantity);
                            singleitemPromotion.setHavePromoted(singleitemPromotion.getHavePromoted() - quantity);

                            hySingleitemPromotionFeignClient.updateSingleItemPromotion(singleitemPromotion);
                        }
                    }
                }
            }

            // 如果是未支付或为出库 则可取消订单
            // 如果使用了余额支付, 将余额退回给用户
            if (state > BUSINESS_ORDER_STATUS_WAIT_FOR_PAY) {
                // 如果已支付 要退款
                // BusinessOrderRefund Entity
                BusinessOrderRefund bRefund = new BusinessOrderRefund();
                BusinessOrder bOrder = businessOrder;
                // 订单
                bRefund.setBusinessOrderId(bOrder.getId());
                // 是否退货
                // bRefund.setIsDelivered(isDelivered);
                // 发货类型
                bRefund.setDeliverType(0);
                // 退款理由
                bRefund.setRefundReason("取消订单");

                Date refundDate = new Date();
                // 退款申请时间
                bRefund.setRefundApplyTime(refundDate);
                // 退款确认时间
                bRefund.setRefundAcceptTime(refundDate);
                // 设置入库时间
                bRefund.setInboundTime(refundDate);
                // 设置物流时间
                bRefund.setShipTime(refundDate);
                // 设置退货完成世界
                bRefund.setReturnCompleteTime(refundDate);
                // 下单微信账户
                bRefund.setWechatId(bOrder.getOrderWechatId());
                /* 退款金额以及设置退货条目 */
                // 订单优惠前总额
                BigDecimal orderTotal = bOrder.getTotalMoney().add(bOrder.getShipFee());
                // 余额支付金额
                BigDecimal orderBalance = bOrder.getBalanceMoney();
                // 其他支付金额
                BigDecimal orderPay = bOrder.getPayMoney();
                // 余额支付退款金额
                BigDecimal rRefundAmount = orderBalance;
                // 其他支付退款金额
                BigDecimal qRefundAmount = orderPay;
                // 货物退款金额
                BigDecimal refundAmount = rRefundAmount.add(qRefundAmount);
                // 设置余额支付退款金额
                bRefund.setRrefundAmount(rRefundAmount);
                // 设置其他支付退款金额
                bRefund.setQrefundAmount(qRefundAmount);
                // 设置货物退款金额
                bRefund.setRefundAmount(refundAmount);
                // 设置少退金额
                bRefund.setErefundAmount(BigDecimal.ZERO);
                // 设置退货物流费
                bRefund.setRefundShipFee(BigDecimal.ZERO);
                // 设置应退款总额
                bRefund.setRefundTotalamount(refundAmount);
                // 设置是否退货
                bRefund.setIsDelivered(false);
                // 设置责任方
                bRefund.setResponsibleParty(1);
                // 状态为待退款
                bRefund.setState(BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_REFUND_MONEY);

                businessOrderRefundService.save(bRefund);

                // 订单设置退款金额
                bOrder.setRefoundMoney(refundAmount);
                // 设置订单为待退款状态
                bOrder.setOrderState(BUSINESS_ORDER_STATUS_WAIT_FOR_REFUND); // 设置订单为待退款状态
                businessOrderMapper.updateRefundMoneyAndOrderState(bOrder);
            } else {
                WechatAccount account = wechatAccountFeignClient.getWechatAccountById(businessOrder.getOrderWechatId());
                // 恢复账户余额并保存
                account.setTotalbalance(account.getTotalbalance().add(businessOrder.getBalanceMoney()));
                wechatAccountFeignClient.updateTotalBalance(account);
                // 取消订单
                businessOrder.setOrderState(BUSINESS_ORDER_STATUS_CANCELED);
                businessOrder.setOrderCancelTime(new Date());
                businessOrderMapper.updateOrderCancelTimeAndOrderState(businessOrder);
            }
            j.setSuccess(true);
            j.setMsg("取消订单成功");
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", businessOrder.getId());
            obj.put("state", businessOrder.getOrderState());
            j.setObj(obj);
        } else {
            j.setSuccess(true);
            j.setMsg("订单状态错误，无法取消订单");
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", businessOrder.getId());
            obj.put("state", businessOrder.getOrderState());
            j.setObj(obj);
        }
        return j;
    }

    private HashMap<String, Object> getOrderListItemMap(BusinessOrder bOrder) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        // 订单id
        map.put("id", bOrder.getId());
        // 订单编号
        map.put("orderCode", bOrder.getOrderCode());
        // 订单状态
        map.put("orderState", bOrder.getOrderState());
        // 支付金额
        map.put("payMoney", bOrder.getPayMoney());
        // 是否由供货商发货
        map.put("isDivided", bOrder.getIsDivided());
        // 是否评价
        map.put("isAppraised", bOrder.getIsAppraised());
        // 订单条目
        List<BusinessOrderItem> itemList = businessOrderItemService.getBusinessOrderItemListByOrderId(bOrder.getId());
        List<HashMap<String, Object>> items = new LinkedList<>();
        for (BusinessOrderItem bItem : itemList) {
            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", bItem.getId());
            itemMap.put("quantity", bItem.getQuantity());
            int itemType = bItem.getType();
            // 普通商品
            if (itemType == 0) {
                Specialty specialty = specialtyFeignClient.getSpecialtyById(bItem.getSpecialtyId());
                itemMap.put("name", specialty.getName());
                List<SpecialtyImage> images = specialtyImageFeignClient.getSpecialtyImageListBySpecialtyId(bItem.getSpecialtyId());
                for (SpecialtyImage image : images) {
                    if (image.getIsLogo()) {
                        itemMap.put("iconURL", image);
                        break;
                    }
                }
                SpecialtySpecification specialtySpecification = specialtySpecificationFeignClient.getSpecialtySpecificationById(bItem.getSpecialtySpecificationId());
                itemMap.put("specification", specialtySpecification.getSpecification());
            } else {
                HyGroupitemPromotion hyGroupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(bItem.getSpecialtyId());
                HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(hyGroupitemPromotion.getPromotionId());
                itemMap.put("name", hyPromotion.getPromotionName());
                List<HyPromotionPic> images = hyPromotionPicFeignClient.getHyPromotionPicListByPromotionId(hyPromotion.getId());
                for (HyPromotionPic image : images) {
                    if (image.getIsTag()) {
                        itemMap.put("iconURL", image);
                    }
                }
                itemMap.put("specification", null);
            }
            itemMap.put("type", itemType);
            itemMap.put("salePrice", bItem.getSalePrice());

            itemMap.put("deliverName", bItem.getDeliverName());
            itemMap.put("deliverType", bItem.getDeliverType());
            itemMap.put("depotName", bItem.getDepotName());

            items.add(itemMap);
        }
        //添加订单条目信息
        map.put("orderItems", items);
        return map;
    }
}