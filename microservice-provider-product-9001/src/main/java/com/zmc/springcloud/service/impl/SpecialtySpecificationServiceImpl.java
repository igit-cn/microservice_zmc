package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.mapper.SpecialtySpecificationMapper;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class SpecialtySpecificationServiceImpl implements SpecialtySpecificationService {
    @Autowired
    private SpecialtySpecificationMapper specialtySpecificationMapper;

    @Autowired
    private HyGroupitemPromotionFeignClient hyGroupitemPromotionFeignClient;

    @Override
    public void batchInsert(List<SpecialtySpecification> specialtySpecificationList) throws Exception {
        specialtySpecificationMapper.batchInsert(specialtySpecificationList);
    }

    @Override
    public List<Map<String, Object>> getParentSpecificationList(Long specialtyid) throws Exception {
        List<SpecialtySpecification> list = specialtySpecificationMapper.getParentSpecificationListByspecialtyid(specialtyid);

        List<Map<String, Object>> res = new ArrayList<>();
        for (SpecialtySpecification specification : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", specification.getId());
            map.put("specification", specification.getSpecification());
            res.add(map);
        }

        return res;
    }

    @Override
    public List<SpecialtySpecification> getAllSpecification(Long id) throws Exception {
        return specialtySpecificationMapper.findAllSpecificationBySpecialtyId(id);
    }

    @Override
    public SpecialtySpecification findById(Long specificationsId) throws Exception {
        return specialtySpecificationMapper.findById(specificationsId);
    }

    @Override
    public void update(SpecialtySpecification specialtySpecification) throws Exception {
        specialtySpecificationMapper.update(specialtySpecification);
    }

    @Override
    public boolean isBaseInboundEnough(List<Map<String, Object>> orderItems) throws Exception {
        for (Map<String, Object> orderItem : orderItems) {
            // 如果是组合优惠
            if ((Boolean) orderItem.get("isGroupPromotion")) {
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(((Integer) orderItem.get("specialtyId")).longValue());
                // 获取购买数量
                Integer quantity = (Integer) orderItem.get("quantity");
                // 检查每件组合优惠明细条目的库存
                List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
                for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                    // 获取明细购买数量
                    Integer total = quantity * detail.getBuyNumber();
                    SpecialtySpecification specification = specialtySpecificationMapper.findById(detail.getItemSpecificationId());
                    // 获取父规格
                    SpecialtySpecification fuSpecification = getParentSpecification(specification);
                    if (fuSpecification == null) {
                        throw new Exception("无效规格异常");
                    }
                    if (total * specification.getSaleNumber() > fuSpecification.getBaseInbound()) {
                        return false;
                    }
                }
            } else {
                // 如果是普通产品
                Integer total = (Integer) orderItem.get("quantity");
                SpecialtySpecification specification = specialtySpecificationMapper.findById(((Integer) orderItem.get("specialtySpecificationId")).longValue());
                // 获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                if (fuSpecification == null) {
                    throw new Exception("无效规格异常");
                }

                if (total * specification.getSaleNumber() > fuSpecification.getBaseInbound()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void updateBaseInboundAndHasSold(BusinessOrderItem orderItem, Boolean isSale) throws Exception {
        if (orderItem.getType() == 0) {
            // 如果是普通商品
            SpecialtySpecification specification = specialtySpecificationMapper.findById(orderItem.getSpecialtySpecificationId());
            // 获取父规格
            SpecialtySpecification fuSpecification = getParentSpecification(specification);
            if (fuSpecification == null) {
                throw new Exception("无效规格异常");
            }
            // 减库存
            Integer soldNumber = specification.getSaleNumber() * orderItem.getQuantity();
            if (isSale) {
                fuSpecification.setBaseInbound(fuSpecification.getBaseInbound() - soldNumber);
                specification.setHasSold(specification.getHasSold() + orderItem.getQuantity());
            } else {
                fuSpecification.setBaseInbound(fuSpecification.getBaseInbound() + soldNumber);
                specification.setHasSold(specification.getHasSold() - orderItem.getQuantity());
            }
            specialtySpecificationMapper.updateInboundAndHasSold(fuSpecification);
            specialtySpecificationMapper.updateInboundAndHasSold(specification);
        } else {
            //如果是组合产品
            //获取组合优惠活动对象
            HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(orderItem.getSpecialtyId());
            //获取购买数量
            Integer quantity = orderItem.getQuantity();
            //修改组合优惠相关数量
            if (isSale) {
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum() - quantity);
            } else {
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum() + quantity);
            }
            //修改每件组合优惠明细条目的库存
            List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
            for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                //获取明细购买数量
                Integer total = quantity * detail.getBuyNumber();
                SpecialtySpecification specification = this.findById(detail.getItemSpecificationId());
                //获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                if (fuSpecification == null) {
                    throw new Exception("无效规格异常");
                }
                //减库存
                Integer soldNumber = specification.getSaleNumber() * total;
                if (isSale) {
                    fuSpecification.setBaseInbound(fuSpecification.getBaseInbound() - soldNumber);
                    specification.setHasSold(specification.getHasSold() + total);
                } else {
                    fuSpecification.setBaseInbound(fuSpecification.getBaseInbound() + soldNumber);
                    specification.setHasSold(specification.getHasSold() - total);
                }
                specialtySpecificationMapper.updateInboundAndHasSold(fuSpecification);
                specialtySpecificationMapper.updateInboundAndHasSold(specification);
            }
            hyGroupitemPromotionFeignClient.updateGroupitemPromotion(groupitemPromotion);
        }
    }

    /**
     * 获取父规格
     */
    private SpecialtySpecification getParentSpecification(SpecialtySpecification specification) throws Exception {
        // 获取父规格
        // TODO 这种写法的前提是顶级规格的pid为0,且规格最多只有两级
        if (specification.getPid() != null && specification.getPid() != 0) {
            return specialtySpecificationMapper.findById(specification.getPid());
        }
        return specification;
    }
}
