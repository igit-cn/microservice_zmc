package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.*;

import com.zmc.springcloud.feignclient.product.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyImageFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionPicFeignClient;
import com.zmc.springcloud.mapper.BusinessOrderItemMapper;
import com.zmc.springcloud.service.BusinessOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class BusinessOrderItemServiceImpl implements BusinessOrderItemService {
    @Autowired
    private BusinessOrderItemMapper businessOrderItemMapper;

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

    @Override
    public BusinessOrderItem getById(Long id) throws Exception {
        return businessOrderItemMapper.findById(id);
    }

    @Override
    public List<BusinessOrderItem> getBusinessOrderItemListByOrderId(Long orderId) {
        return businessOrderItemMapper.findListByOrderId(orderId);
    }

    @Override
    public void save(BusinessOrderItem businessOrderItem) throws Exception {
        businessOrderItemMapper.insert(businessOrderItem);
    }

    @Override
    public List<Map<String, Object>> getRefundItemMapList(BusinessOrder order) {
        List<Map<String, Object>> refundList=new LinkedList<>();
        List<BusinessOrderItem> itemList = businessOrderItemMapper.getListByOrderIdAndReturnQuantity(order.getId(), 0);
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
            itemMap.put("returnQuantity",bItem.getReturnQuantity());
            refundList.add(itemMap);
        }
        return refundList;
    }
}
