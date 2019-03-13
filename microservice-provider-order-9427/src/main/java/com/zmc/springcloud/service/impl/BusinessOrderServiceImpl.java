package com.zmc.springcloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.common.CommonSequenceFeignClient;
import com.zmc.springcloud.feignclient.express.HyVinboundFeignClient;
import com.zmc.springcloud.feignclient.express.InboundFeignClient;
import com.zmc.springcloud.feignclient.login.HyAdminFeignClient;

import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.feignclient.express.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HySingleitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.supplier.ProviderFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.*;
import com.zmc.springcloud.mapper.BusinessOrderMapper;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.utils.OrderTransactionSNGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.zmc.springcloud.utils.CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_REVIEW;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {
    @Autowired
    private BusinessOrderMapper businessOrderMapper;

    @Autowired
    private ProviderFeignClient providerFeignClient;

    @Autowired
    private HySingleitemPromotionFeignClient hySingleitemPromotionFeignClient;

    @Autowired
    private HyGroupitemPromotionFeignClient hyGroupitemPromotionFeignClient;

    @Autowired
    private HyAdminFeignClient hyAdminFeignClient;

    @Autowired
    private CommonSequenceFeignClient commonSequenceFeignClient;

    @Autowired
    private HyVinboundFeignClient hyVinboundFeignClient;

    @Autowired
    private InboundFeignClient inboundFeignClient;

    @Autowired
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    @Autowired
    private HyPromotionFeignClient hyPromotionFeignClient;

    @Autowired
    private SpecialtyFeignClient specialtyFeignClient;

    @Autowired
    private CouponGiftFeignClient couponGiftFeignClient;

    @Autowired
    private CouponBalanceUseFeignClient couponBalanceUseFeignClient;

    @Autowired
    private PointRecordFeignClient pointRecordFeignClient;

    @Autowired
    private VipFeignClient vipFeignClient;

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private OrderTransactionService orderTransactionService;

    @Autowired
    private BusinessOrderItemService businessOrderItemService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private BusinessOrderOutboundService businessOrderOutboundService;

    @Autowired
    private BusinessOrderRefundService businessOrderRefundService;

    @Override
    public HashMap<String, Object> getOrderOList(Integer page, Integer rows, Integer orderState, String orderCode, String orderPhone) throws Exception {
        PageHelper.startPage(page, rows);
        List<BusinessOrder> list = businessOrderMapper.findListOrder(orderState, orderCode, orderPhone);
        PageInfo pageInfo = new PageInfo(list);
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("rows", list);
        obj.put("pageNumber", page);
        obj.put("pageSize", rows);
        obj.put("total", pageInfo.getTotal());
        obj.put("totalPages", pageInfo.getPages());
        return obj;
    }

    @Override
    public HashMap<String, Object> getOrderDetail(Long orderId) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        BusinessOrder order = businessOrderMapper.findById(orderId);
        map.put("order", order);

        List<Map<String, Object>> orderItems = new ArrayList<>();
        List<BusinessOrderItem> businessOrderItemList = businessOrderItemService.getBusinessOrderItemListByOrderId(orderId);
        for (BusinessOrderItem item : businessOrderItemList) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", item.getId());
            m.put("name", getSpecialtyName(item));
            m.put("specificationname", getSpecificationName(item));
            PurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(item.getPurchaseItemId());
            m.put("purchaseItem", purchaseItem);
            m.put("quantity", item.getQuantity());
            m.put("outboundQuantity", item.getOutboundQuantity());
            m.put("outboundTime", item.getOutboundTime());
            m.put("operator", item.getOperator());
            m.put("returnQuantity", item.getReturnQuantity());
            m.put("salePrice", item.getSalePrice());
            m.put("originalPrice", item.getOriginalPrice());
            m.put("isappraised", item.getIsappraised());
            m.put("promotionId", item.getPromotionId());
            m.put("type", item.getType());
            m.put("isDelivered", item.getIsDelivered());
            m.put("deliverName", item.getDeliverName());
            m.put("deliverType", item.getDeliverType());
            m.put("depotName", item.getDepotName());

            if (item.getPromotionId() != null) {
                HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(item.getPromotionId());
                m.put("promotionName", hyPromotion.getPromotionName());
                m.put("promotionRule", hyPromotion.getPromotionRule());
                m.put("promotionType", hyPromotion.getPromotionType());
            } else {
                m.put("promotionName", null);
                m.put("promotionRule", null);
                m.put("promotionType", null);
            }

            // 获取可发货仓库列表
            if (item.getType() == 0) {
                // 普通商品
                SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(item.getSpecialtySpecificationId());
                Integer packNumber = specification.getSaleNumber();
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                m.put("depotList", getDepotList(fuSpecification.getId(), item.getQuantity() * packNumber));
                orderItems.add(m);
            } else {
                // 组合产品
                List<String> depotList = new ArrayList<>();
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(item.getSpecialtyId());
                // 获取每件组合优惠明细条目的库存
                List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
                for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                    SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(detail.getItemSpecificationId());
                    Integer packNumber = specification.getSaleNumber();
                    //获取父规格
                    SpecialtySpecification fuSpecification = getParentSpecification(specification);
                    List<String> subDepotList = getDepotList(fuSpecification.getId(), item.getQuantity() * packNumber);
                    List<String> tmp = new ArrayList<>();
                    if (depotList == null || depotList.isEmpty()) {
                        tmp.addAll(subDepotList);
                    } else {
                        for (String depot : depotList) {
                            for (String subDepot : subDepotList) {
                                if (depot.equals(subDepot)) {
                                    tmp.add(depot);
                                }
                            }
                        }
                    }
                    depotList = tmp;
                    if (depotList == null || depotList.isEmpty()) {
                        break;
                    }
                }
                m.put("depotList", depotList);
                orderItems.add(m);
            }
        }
        map.put("orderItems", orderItems);
        //设置物流信息
        Map<String, Object> shipMap = new HashMap<>();
        if (order.getShipId() != null) {
            Ship ship = shipService.getShipById(order.getShipId());
            shipMap.put("type", ship.getType());
            shipMap.put("receiveType", order.getReceiveType());
            shipMap.put("receiverName", order.getReceiverName());
            shipMap.put("receiverPhone", order.getReceiverPhone());
            shipMap.put("receiverAddress", order.getReceiverAddress());
            shipMap.put("receiverRemark", order.getReceiverRemark());
            shipMap.put("recordTime", ship.getRecordTime());
            HyAdmin hyAdmin = hyAdminFeignClient.getHyAdminByUserName(ship.getDeliverOperator());
            shipMap.put("deliveror", hyAdmin.getName());
            shipMap.put("shipCompany", ship.getShipCompany());
            shipMap.put("shipCode", ship.getShipCode());
            map.put("ship", shipMap);
        } else {
            map.put("ship", null);
        }

        //退货条目
        List<Map<String, Object>> returnedInboundDetailList = new ArrayList<>();
        for (BusinessOrderItem item : businessOrderItemList) {
            if (item.getReturnQuantity() != null && item.getReturnQuantity() > 0) {
                Map<String, Object> m = new HashMap<>();
                m.put("itemId", item.getId());
                m.put("name", getSpecialtyName(item));
                m.put("specification", getSpecificationName(item));
                m.put("inboundQuantity", item.getReturnQuantity());
                returnedInboundDetailList.add(m);
            }
        }
        map.put("returnedInboundList", returnedInboundDetailList);
        List<BusinessOrderOutbound> outbounds = new ArrayList<>();
        for (BusinessOrderItem item : businessOrderItemList) {
            List<BusinessOrderOutbound> items = businessOrderOutboundService.getListByBusinessOrderItemId(item.getId());
            if (items.size() > 0) {
                outbounds.add(items.get(0));
            }
        }
        map.put("outboundList", outbounds);
        List<BusinessOrderRefund> refunds = businessOrderRefundService.getListByBusinessOrderId(order.getId());
        if (refunds.size() > 0) {
            map.put("orderRefund", refunds.get(0));
        } else {
            map.put("orderRefund", null);
        }
        return map;
    }

    @Override
    public Json verifyAgree(JSONObject body, String usernameInSession) throws Exception {
        Json j = new Json();
        // 解析JSON数据
        Long id = body.getLong("id");
        Boolean isDivided = body.getBoolean("isDivided");
        JSONArray jsonArray = body.getJSONArray("items");

        HyAdmin admin = hyAdminFeignClient.getHyAdminByUserName(usernameInSession);
        BusinessOrder order = businessOrderMapper.findById(id);
        if(order==null){
            j.setSuccess(false);
            j.setMsg("订单不存在");
            return j;
        }
        if(!CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_REVIEW.equals(order.getOrderState())){
            j.setSuccess(false);
            j.setMsg("订单状态不对");
            j.setObj(null);
            return j;
        }
        Map<String, List<BusinessOrderItem>> map=new HashMap<>();
        //构建发货商-list<businessOrderItem> map
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            Long itemId = obj.getLong("id");
            Integer deliverType = obj.getInteger("deliverType");
            String depotName  = obj.getString("depotName");

            BusinessOrderItem orderItem = businessOrderItemService.getById(itemId);
            if(deliverType == 0 || orderItem.getType() == 1){
                // 如果由平台发货或是组合产品
                // 设置由平台发货
                orderItem.setDeliverType(0);
                // 设置发货仓库
                orderItem.setDepotName(depotName);
                if(!map.containsKey("平台")){
                    map.put("平台", new ArrayList<>());
                }
                // 添加orderItem到相应供应商list中
                map.get("平台").add(orderItem);
            }else{
                // 设置由供货商发货
                orderItem.setDeliverType(1);
                // 设置发货仓库为null
                orderItem.setDepotName(null);
                String deliverName = orderItem.getDeliverName();
                if(null == deliverName){
                    j.setSuccess(false);
                    j.setMsg("数据异常，订单条目供应商不存在");
                    return j;
                }
                if(!map.containsKey(deliverName)){
                    map.put(deliverName, new ArrayList<>());
                }
                // 添加orderItem到相应供应商list中
                map.get(deliverName).add(orderItem);
            }
        }

        // 判断是否有平台发货，库存是否不足
        if(map.containsKey("平台")){
            List<BusinessOrderItem> items = map.get("平台");
            BusinessOrderItem item = isInboundEnough(items);
            if(item != null){
                // 判断库存, 如果库存不足
                j.setSuccess(false);
                j.setMsg("库存不足");
                j.setObj(item);
                return j;
            }
        }

        //设置审核人
        order.setReviewer(usernameInSession);
        //设置是否由供应商发货
        order.setIsDivided(isDivided);
        //设置父订单id为0
        order.setParentOrderId(0L);
        //如果map长度为一
        if (map.size() == 1) {
            //不需要拆分订单
            //不是原始凭据
            order.setIsShow(false);
            order.setOrderState(map.containsKey("平台") ? CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_INBOUND :
                    CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_DELIVERY);
            order.setReviewTime(new Date());

        } else {
            //否则需要拆分订单
            //设置为是原始凭证
            order.setIsShow(true);
            for (Map.Entry<String, List<BusinessOrderItem>> item : map.entrySet()) {
                //创建子订单
                createSubOrder(order, item.getKey(), item.getValue());
            }
            //设置父订单为完成状态
            order.setOrderState(CommonAttributes.BUSINESS_ORDER_STATUS_FINISH);
            order.setReviewTime(new Date());
        }

        // 更新父订单信息
        businessOrderMapper.updateBusinessOrder(order);

        //修改所有子订单条目库存
        for(Map.Entry<String,List<BusinessOrderItem>> entry:map.entrySet()){
            //如果由供应商发货
            if(!entry.getKey().equals("平台")){
                List<BusinessOrderItem> items = entry.getValue();
                for(BusinessOrderItem item:items){
                    item.setOutboundQuantity(item.getQuantity());
                    item.setOperator(usernameInSession);
                    updateOrderItemVinbound(item);
                }
            }
        }
        j.setSuccess(true);
        j.setMsg("审核通过");
        return j;
    }


    /** 创建子订单*/
    private BusinessOrder createSubOrder(BusinessOrder order,String deliverName,List<BusinessOrderItem> orderItems) throws Exception{

        //获取子订单总价
        BigDecimal subTotal = BigDecimal.ZERO;
        for(BusinessOrderItem item:orderItems){
            subTotal=subTotal.add(item.getSalePrice().multiply(BigDecimal.valueOf(item.getQuantity().longValue())));
        }
        BigDecimal subRatio = subTotal.divide(order.getShouldpayMoney().subtract(order.getShipFee()), 3, BigDecimal.ROUND_HALF_DOWN);
        //创建子订单
        BusinessOrder subOrder = new BusinessOrder();
        //设置下单手机号
        subOrder.setOrderPhone(order.getOrderPhone());
        //设置总价
        subOrder.setTotalMoney(subTotal);
        //设置优惠金额
        subOrder.setPromotionAmount(order.getPromotionAmount().multiply(subRatio));
        //设置快递费
        subOrder.setShipFee(order.getShipFee().multiply(subRatio));
        //设置应付金额
        subOrder.setShouldpayMoney(order.getShouldpayMoney().multiply(subRatio));
        //设置电子卷金额
        subOrder.setCouponMoney(order.getCouponMoney().multiply(subRatio));
        //设置余额金额
        subOrder.setBalanceMoney(order.getBalanceMoney().multiply(subRatio));
        //设置支付金额
        subOrder.setPayMoney(order.getPayMoney().multiply(subRatio));
        //设置收货备注
        subOrder.setReceiverRemark(order.getReceiverRemark());
        //设置收货地址
        subOrder.setReceiverAddress(order.getReceiverAddress());
        //设置收货人姓名
        subOrder.setReceiverName(order.getReceiverName());
        //设置收货人手机号
        subOrder.setReceiverPhone(order.getReceiverPhone());
        //设置收货类型
        subOrder.setReceiveType(order.getReceiveType());
        //设置微信下单账户
        subOrder.setOrderWechatId(order.getOrderWechatId());
        //设置微商
        subOrder.setWeBusinessId(order.getWeBusinessId());

        //设置订单编号
        String code = commonSequenceFeignClient.getCode(CommonSequence.SequenceTypeEnum.businessOrderSuq, null);
        subOrder.setOrderCode(code);
        //设置下单时间
        subOrder.setOrderTime(order.getOrderTime());
        //设置支付时间
        subOrder.setPayTime(order.getPayTime());
        //设置电子卷id
        subOrder.setCouponId(order.getCouponId());
        //设置为有效
        subOrder.setIsValid(true);

        //设置审核人
        subOrder.setReviewer(order.getReviewer());
        //设置父订单id
        subOrder.setParentOrderId(order.getId());
        Boolean isDeliver = !"平台".equals(deliverName);
        //设置是否由供应商发货
        subOrder.setIsDivided(isDeliver);
        //不是原始凭证
        subOrder.setIsShow(false);
        //设置订单状态
        subOrder.setOrderState(isDeliver?CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_DELIVERY: CommonAttributes.BUSINESS_ORDER_STATUS_WAIT_FOR_INBOUND);
        subOrder.setReviewTime(new Date());

        businessOrderMapper.saveBusinessOrder(subOrder);

        //设置订单条目
        for(BusinessOrderItem item:orderItems){
            //设置该订单条目的订单id为子订单的id
            businessOrderItemService.setBusinessOrder(item, subOrder.getId());
        }

        return subOrder;
    }

    private String getSpecialtyName(BusinessOrderItem item) throws Exception {
        Integer itemType = item.getType();
        // 普通商品
        if (itemType == 0) {
            Specialty specialty = specialtyFeignClient.getSpecialtyById(item.getSpecialtyId());
            return specialty.getName();
        } else {
            //组合优惠
            HyGroupitemPromotion hyGroupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(item.getSpecialtyId());
            HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(hyGroupitemPromotion.getPromotionId());
            return hyPromotion.getPromotionName();
        }
    }

    private String getSpecificationName(BusinessOrderItem item) throws Exception {
        Integer itemType = item.getType();
        // 普通商品
        if (itemType == 0) {
            Specialty specialty = specialtyFeignClient.getSpecialtyById(item.getSpecialtyId());
            return specialty.getName();
        } else {
            return null;
        }
    }

    public List<String> getDepotList(Long specificationId, Integer quantity) throws Exception {
        List<Inbound> inbounds = inboundFeignClient.getInboundListBySpecificationId(specificationId, 0);

        Map<String, Integer> map = new HashMap<>();
        for (Inbound inbound : inbounds) {
            if (!map.containsKey(inbound.getDepotCode())) {
                map.put(inbound.getDepotCode(), 0);
            }
            String depotName = inbound.getDepotCode();
            map.put(depotName, map.get(depotName) + inbound.getInboundNumber());
        }

        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= quantity) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private BusinessOrderItem isInboundEnough(List<BusinessOrderItem> items) throws Exception{
        for (BusinessOrderItem item : items) {
            if(item.getType() == 0){
                // 如果是普通商品
                SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(item.getSpecialtySpecificationId());
                // 获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                List<Inbound> inbounds = inboundFeignClient.getListBySpecificationIdAndDepotCode(specification.getId(), item.getDepotName());
                Integer inboundsTotal = getInboundTotal(inbounds);
                Integer total = item.getQuantity() * specification.getSaleNumber();
                if(inboundsTotal<total){
                    return item;
                }
            }else{
                // 如果是组合产品
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(item.getSpecialtyId());
                // 获取构建数量
                Integer quantity = item.getQuantity();
                // 获取每件组合优惠明细条目的库存
                List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
                for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                    SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(detail.getItemSpecificationId());
                    // 获取父规格
                    SpecialtySpecification fuSpecification = getParentSpecification(specification);
                    List<Inbound> inbounds = inboundFeignClient.getListBySpecificationIdAndDepotCode(specification.getId(), item.getDepotName());
                    Integer inboundsTotal = getInboundTotal(inbounds);
                    Integer total = quantity*detail.getBuyNumber()*specification.getSaleNumber();

                    if(inboundsTotal<total){
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public Integer getInboundTotal(List<Inbound> inbounds) throws Exception {
        Integer total = 0;
        for (Inbound inbound : inbounds) {
            total += inbound.getInboundNumber();
        }
        return total;
    }

    /** 获取父规格*/
    private SpecialtySpecification getParentSpecification(SpecialtySpecification specification) throws Exception{
        // 获取父规格
        // TODO 这种写法的前提是顶级规格的pid为0,且规格最多只有两级
        if (specification.getPid() != null && specification.getPid() != 0) {
            return specialtySpecificationFeignClient.getSpecialtySpecificationById(specification.getPid());
        }
        return specification;
    }

    private void updateOrderItemVinbound(BusinessOrderItem item) throws Exception{
        if(item.getType() == 0){
            // 如果是普通商品
            SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(item.getSpecialtySpecificationId());
            //获取父规格
            SpecialtySpecification fuSpecification = getParentSpecification(specification);
            List<HyVinbound> vinbounds = hyVinboundFeignClient.getHyVinboundListBySpecificationId(fuSpecification.getId());
            if(vinbounds == null || vinbounds.isEmpty()){
                return;
            }
            HyVinbound vinbound = vinbounds.get(0);
            // 减虚拟库存
            abstractVinbound(item.getQuantity()*specification.getSaleNumber(),vinbound);
        }else{
            // 如果是组合商品
            // 获取组合优惠活动对象
            HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(item.getSpecialtyId());
            // 获取构建数量
            Integer quantity = item.getQuantity();
            // 获取每件组合优惠明细条目的库存
            List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
            for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                //获取明细购买数量
                Integer total = quantity*detail.getBuyNumber();
                SpecialtySpecification specification = specialtySpecificationFeignClient.getSpecialtySpecificationById(detail.getItemSpecificationId());
                // 获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                List<HyVinbound> vinbounds = hyVinboundFeignClient.getHyVinboundListBySpecificationId(fuSpecification.getId());
                if(vinbounds == null || vinbounds.isEmpty()){
                    continue;
                }
                HyVinbound vinbound = vinbounds.get(0);
                //减虚拟库存
                abstractVinbound(total*specification.getSaleNumber(),vinbound);
            }
        }
    }

    /** 减虚拟库存*/
    private void abstractVinbound(Integer total, HyVinbound vinbound) throws Exception {
        Integer saleNumber = vinbound.getSaleNumber();
        Integer vinboundNumber = vinbound.getVinboundNumber();
        saleNumber += total;
        vinboundNumber = vinboundNumber < total ? 0 : vinboundNumber - total;
        vinbound.setSaleNumber(saleNumber);
        vinbound.setVinboundNumber(vinboundNumber);
        hyVinboundFeignClient.updateHyVinbound(vinbound);
    }


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
        if(!specialtySpecificationFeignClient.isBaseInboundEnough(orderItems)){
            throw new Exception("库存不足，无法下单");
        }
        /* 判断优惠活动数量是否满足 */
        for (Map<String, Object> orderItem : orderItems){
            Boolean isGroupPromotion = (Boolean) orderItem.get("isGroupPromotion");
            // 如果是组合产品
            if (isGroupPromotion) {
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(((Integer) orderItem.get("specialtyId")).longValue());
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
                    HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(((Integer) orderItem.get("specialtySpecificationId")).longValue(), promotionId);
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
        WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(wechatId);
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
        String code = commonSequenceFeignClient.getCode(CommonSequence.SequenceTypeEnum.orderSn, null);
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
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(((Integer) orderItem.get("specialtyId")).longValue());
                // 获取购买数量
                Integer quantity = (Integer) orderItem.get("quantity");
                // 修改优惠活动数量
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum() - quantity);
                groupitemPromotion.setHavePromoted(groupitemPromotion.getHavePromoted() + quantity);
                hyGroupitemPromotionFeignClient.updateGroupitemPromotion(groupitemPromotion);
            }else {
                // 如果是普通产品
                // 获取购买数量
                if(orderItem.get("promotionId") != null) {
                    Long promotionId = ((Integer) orderItem.get("promotionId")).longValue();
                    Integer quantity = (Integer) orderItem.get("quantity");
                    // 获取优惠明细
                    HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(((Integer) orderItem.get("specialtySpecificationId")).longValue(), promotionId);
                    // 如果参加了优惠活动
                    if (singleitemPromotion != null) {
                        // 修改优惠数量
                        singleitemPromotion.setPromoteNum(singleitemPromotion.getPromoteNum() - quantity);
                        singleitemPromotion.setHavePromoted(singleitemPromotion.getHavePromoted() + quantity);
                        hySingleitemPromotionFeignClient.updateSingleItemPromotion(singleitemPromotion);
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
            wechatAccountFeignClient.updateTotalBalance(wechatAccount);
        }
        for (Integer couponId : coupons) {
            CouponGift coupon = couponGiftFeignClient.getCouponGiftById(couponId.longValue());
            if(coupon != null){
                // 设置为已使用 1 已使用
                coupon.setState(1);
                // 设置使用时间
                coupon.setUseTime(new Date());
                couponGiftFeignClient.updateUseState(coupon);
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
                Specialty specialty = specialtyFeignClient.getSpecialtyById(businessOrderItem.getSpecialtyId());
                Provider provider = providerFeignClient.getProviderById(specialty.getProviderId());
                businessOrderItem.setDeliverName(provider.getProviderName());
            }
            businessOrderItemService.save(businessOrderItem);

            //更新规格的基础库存和销量
            specialtySpecificationFeignClient.updateBaseInboundAndHasSold(businessOrderItem, true);
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
            WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(businessOrder.getOrderWechatId());
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
                    vipFeignClient.setVip318(wechatAccount, money);

                    if(changevalue != 0){
                        pointRecordFeignClient.changeUserPoint(businessOrder.getOrderWechatId(),changevalue , "购物");
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
                        wechatAccountFeignClient.updateVipPointTotalpointTotalbalance(wechatAccount);

                        // 添加余额兑换记录
                        CouponBalanceUse couponBalanceUse = new CouponBalanceUse();
                        couponBalanceUse.setPhone(wechatAccount.getPhone());
                        // 6首单奖励
                        couponBalanceUse.setType(6);
                        couponBalanceUse.setState(1);
                        couponBalanceUse.setUseAmount(awardMoney.floatValue());
                        couponBalanceUse.setUseTime(new Date());
                        couponBalanceUse.setWechatId(wechatAccount.getId());
                        couponBalanceUseFeignClient.save(couponBalanceUse);
                    }
                }
            }
            //下单成功，修改用户是否为新用户
            if(wechatAccount.getIsNew()){
                wechatAccount.setIsNew(false);
                wechatAccountFeignClient.updateIsNew(wechatAccount);
            }
        }
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
