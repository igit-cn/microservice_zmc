package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.mapper.SpecialtySpecificationMapper;
import com.zmc.springcloud.service.HyGroupitemPromotionDetailService;
import com.zmc.springcloud.service.HyGroupitemPromotionService;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class SpecialtySpecificationServiceImpl implements SpecialtySpecificationService {
    @Autowired
    private SpecialtySpecificationMapper specialtySpecificationMapper;

    @Autowired
    private HyGroupitemPromotionService hyGroupitemPromotionService;

    @Autowired
    private HyGroupitemPromotionDetailService hyGroupitemPromotionDetailService;

    @Override
    public SpecialtySpecification find(Long specialtySpecificationId) throws Exception{
        return specialtySpecificationMapper.findById(specialtySpecificationId);
    }


    @Override
    public boolean isBaseInboundEnough(List<Map<String, Object>> orderItems) throws Exception {
        for (Map<String, Object> orderItem : orderItems) {
            // 如果是组合优惠
            if ((Boolean) orderItem.get("isGroupPromotion")) {
                // 获取组合优惠活动对象
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionService.find(((Integer) orderItem.get("specialtyId")).longValue());
                // 获取购买数量
                Integer quantity = (Integer) orderItem.get("quantity");
                // 检查每件组合优惠明细条目的库存
                List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionDetailService.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
                for(HyGroupitemPromotionDetail detail:hyGroupitemPromotionDetails){
                    // 获取明细购买数量
                    Integer total = quantity*detail.getBuyNumber();
                    SpecialtySpecification specification = specialtySpecificationMapper.findById(detail.getItemSpecificationId());
                    // 获取父规格
                    SpecialtySpecification fuSpecification = getParentSpecification(specification);
                    if(fuSpecification==null){
                        throw new Exception("无效规格异常");
                    }
                    if(total*specification.getSaleNumber()>fuSpecification.getBaseInbound()){
                        return false;
                    }
                }
            } else {
                // 如果是普通产品
                Integer total = (Integer) orderItem.get("quantity");
                SpecialtySpecification specification = specialtySpecificationMapper.findById(((Integer) orderItem.get("specialtySpecificationId")).longValue());
                // 获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                if(fuSpecification==null){
                    throw new Exception("无效规格异常");
                }

                if(total*specification.getSaleNumber()>fuSpecification.getBaseInbound()){
                    return false;
                }
            }
        }
        return true;
    }

    /** 获取父规格*/
    private SpecialtySpecification getParentSpecification(SpecialtySpecification specification) throws Exception{
        // 获取父规格
        // TODO 这种写法的前提是顶级规格的pid为0,且规格最多只有两级
        if (specification.getPid() != null && specification.getPid() != 0) {
            return specialtySpecificationMapper.findById(specification.getPid());
        }
        return specification;
    }

    @Override
    public void updateBaseInboundAndHasSold(BusinessOrderItem orderItem, Boolean isSale) throws Exception {
        if(orderItem.getType() == 0){
            // 如果是普通商品
            SpecialtySpecification specification = specialtySpecificationMapper.findById(orderItem.getSpecialtySpecificationId());
            // 获取父规格
            SpecialtySpecification fuSpecification = getParentSpecification(specification);
            if(fuSpecification == null){
                throw new Exception("无效规格异常");
            }
            // 减库存
            Integer soldNumber = specification.getSaleNumber() * orderItem.getQuantity();
            if(isSale){
                fuSpecification.setBaseInbound(fuSpecification.getBaseInbound()-soldNumber);
                specification.setHasSold(specification.getHasSold()+orderItem.getQuantity());
            }else{
                fuSpecification.setBaseInbound(fuSpecification.getBaseInbound()+soldNumber);
                specification.setHasSold(specification.getHasSold()-orderItem.getQuantity());
            }
            specialtySpecificationMapper.updateInboundAndHasSold(fuSpecification);
            specialtySpecificationMapper.updateInboundAndHasSold(specification);
        }else{
            //如果是组合产品
            //获取组合优惠活动对象
            HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionService.find(orderItem.getSpecialtyId());
            //获取购买数量
            Integer quantity = orderItem.getQuantity();
            //修改组合优惠相关数量
            if(isSale){
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum()-quantity);
            }else {
                groupitemPromotion.setPromoteNum(groupitemPromotion.getPromoteNum()+quantity);
            }
            //修改每件组合优惠明细条目的库存
            List<HyGroupitemPromotionDetail> hyGroupitemPromotionDetails = hyGroupitemPromotionDetailService.getHyGroupitemPromotionDetailList(groupitemPromotion.getId());
            for (HyGroupitemPromotionDetail detail : hyGroupitemPromotionDetails) {
                //获取明细购买数量
                Integer total = quantity*detail.getBuyNumber();
                SpecialtySpecification specification = this.find(detail.getItemSpecificationId());
                //获取父规格
                SpecialtySpecification fuSpecification = getParentSpecification(specification);
                if(fuSpecification==null){
                    throw new Exception("无效规格异常");
                }
                //减库存
                Integer soldNumber = specification.getSaleNumber()*total;
                if(isSale){
                    fuSpecification.setBaseInbound(fuSpecification.getBaseInbound()-soldNumber);
                    specification.setHasSold(specification.getHasSold()+total);
                }else{
                    fuSpecification.setBaseInbound(fuSpecification.getBaseInbound()+soldNumber);
                    specification.setHasSold(specification.getHasSold()-total);
                }
                specialtySpecificationMapper.updateInboundAndHasSold(fuSpecification);
                specialtySpecificationMapper.updateInboundAndHasSold(specification);
            }
            hyGroupitemPromotionService.updatePromotion(groupitemPromotion);
        }
    }
}
