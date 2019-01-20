package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.express.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyImageFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionPicFeignClient;
import com.zmc.springcloud.mapper.BusinessOrderMapper;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService{
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
    private ShipService shipService;

    @Autowired
    private BusinessOrderRefundService businessOrderRefundService;

    @Override
    public HashMap<String, Object> getOrderListByAccount(Integer page, Integer rows, Long wechatId, Integer status, Boolean isValid, Boolean isShow) throws Exception {
        PageHelper.startPage(page, rows);
        List<BusinessOrder> businessOrderList = businessOrderMapper.getOrderListByAccount(wechatId, status, isValid, isShow);
        PageInfo pageInfo = new PageInfo(businessOrderList);
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("rows", businessOrderList);
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
            if(itemType == 0){
                Specialty specialty = specialtyFeignClient.getSpecialtyById(bItem.getSpecialtyId());
                itemMap.put("name", specialty.getName());
                List<SpecialtyImage> images = specialtyImageFeignClient.getSpecialtyImageListBySpecialtyId(bItem.getSpecialtyId());
                for (SpecialtyImage image : images) {
                    if(image.getIsLogo()){
                        itemMap.put("iconURL", image);
                        break;
                    }
                }
                SpecialtySpecification specialtySpecification = specialtySpecificationFeignClient.getSpecialtySpecificationById(bItem.getSpecialtySpecificationId());
                itemMap.put("specification", specialtySpecification.getSpecification());
            }else{
                HyGroupitemPromotion hyGroupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(bItem.getSpecialtyId());
                HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(hyGroupitemPromotion.getPromotionId());
                itemMap.put("name", hyPromotion.getPromotionName());
                List<HyPromotionPic> images = hyPromotionPicFeignClient.getHyPromotionPicListByPromotionId(hyPromotion.getId());
                for (HyPromotionPic image : images) {
                    if(image.getIsTag()){
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
        List<BusinessOrderRefund> orderRefunds = businessOrderRefundService.getListByBusinessOrderId(orderId);
        if(CollectionUtils.isEmpty(orderRefunds)){
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
        HashMap<String, Object> orderMap = businessOrderService.getOrderListItemMap(order);
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
}
