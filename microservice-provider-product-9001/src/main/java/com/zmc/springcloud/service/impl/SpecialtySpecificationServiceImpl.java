package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HySingleitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WeBusinessFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WeDivideModelFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.mapper.SpecialtySpecificationMapper;
import com.zmc.springcloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private WeBusinessFeignClient weBusinessFeignClient;

    @Autowired
    private WeDivideModelFeignClient weDivideModelFeignClient;

    @Autowired
    private HySingleitemPromotionFeignClient hySingleitemPromotionFeignClient;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialtyImageService specialtyImageService;

    @Autowired
    private SpecialtyPriceService specialtyPriceService;

    @Autowired
    private HyLabelService hyLabelService;

    @Autowired
    private HySpecialtyLabelService hySpecialtyLabelService;

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

    @Override
    public List<Map<String, Object>> getSpecificationDetailBySpecialtyIdAndWechatId(Long specialtyId, Long wechatId) throws Exception {
        List<SpecialtySpecification> srows = specialtySpecificationMapper.findAllSpecificationBySpecialtyId(specialtyId);
        List<Map<String, Object>> rows = filterSpecificationPrice(doSpecialtyList(srows, wechatId));
        return rows;
    }

    @Override
    public List<Map<String, Object>> getSpecificationDetailBySpecialtyId(Long specialtyId, Long wechatId) throws Exception{
        SpecialtySpecification srows = specialtySpecificationMapper.findById(specialtyId);
        List<SpecialtySpecification> list = new ArrayList<>();
        list.add(srows);
        List<Map<String, Object>> rows = filterSpecificationPrice(doSpecialtyList(list, wechatId));
        return rows;
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

    /**
     * 重新处理List，获得新的List
     */
    private List<Map<String, Object>> doSpecialtyList(List<SpecialtySpecification> srows, Long wechatId) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<Long, Integer> hasSolds = new HashMap<>();

        for (SpecialtySpecification s : srows) {
            Map<String, Object> map = new HashMap<>();
            Specialty specialty = specialtyService.getSpecialtyById(s.getSpecialtyId());
            if (specialty.getIsActive() == null || !specialty.getIsBanner() || specialty.getSaleState() == null
                    || specialty.getSaleState() == 0 || s.getIsActive() == null || !s.getIsActive()) {
                continue;
            }
            // 产品信息
            map.put("specialty", specialty);

            // 产品图标
            List<SpecialtyImage> images = specialtyImageService.getSpecialtyImageList(s.getSpecialtyId());
            for (SpecialtyImage image : images) {
                if(image.getIsLogo() != null && image.getIsLogo()){
                    map.put("iconURL", image);
                    break;
                }
            }
            // 规格信息
            map.put("specification", s);

            // 找价格
            // 先去价格变化表里查
            SpecialtyPrice price = specialtyPriceService.find(s.getId(), true);
            if(price == null){
                continue;
            }
            map.put("mPrice", price.getMarketPrice());
            map.put("pPrice", price.getPlatformPrice());
            // 运费
            map.put("deliverPrice", price.getDeliverPrice());
            // 找库存
            // 找父规格
            SpecialtySpecification fuSpecification = this.getParentSpecification(s);
            if(fuSpecification == null){
                continue;
            }
            Integer totalInbound = fuSpecification.getBaseInbound();
            if(totalInbound == 0){
                continue;
            }
            map.put("inbound", totalInbound / s.getSaleNumber());
            // 找提成比例
            WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(wechatId);
            if(wechatAccount != null){
                List<WeBusiness> weBusinesses = weBusinessFeignClient.getWeBusinessListByOpenId(wechatAccount.getWechatOpenid());
                if(weBusinesses != null && !weBusinesses.isEmpty()){
                    WeBusiness weBusiness = weBusinesses.get(0);
                    switch (weBusiness.getType()){
                        case 0:
                            List<WeDivideModel> weDivideModels = weDivideModelFeignClient.getWeDivideModelListByModelTypeAndIsValid("虹宇门店", true);
                            map.put("divideRatio", price.getStoreDivide().multiply(weDivideModels.get(0).getWeBusiness()));
                            break;
                        case 1:
                            List<WeDivideModel> weDivideModels1 = weDivideModelFeignClient.getWeDivideModelListByModelTypeAndIsValid("非虹宇门店", true);
                            map.put("divideRatio", price.getStoreDivide().multiply(weDivideModels1.get(0).getWeBusiness()));
                            break;
                        case 2:
                            List<WeDivideModel> weDivideModels2 = weDivideModelFeignClient.getWeDivideModelListByModelTypeAndIsValid("个人商贸", true);
                            map.put("divideRatio", price.getStoreDivide().multiply(weDivideModels2.get(0).getWeBusiness()));
                            break;
                        default:
                            break;
                    }
                } else {
                    map.put("divideRatio", null);
                }
            } else {
                map.put("divideRatio", null);
            }
            // 提成金额
            if (map.get("divideRatio") != null) {
                map.put("divideMoney", price.getPlatformPrice().subtract(price.getCostPrice()).subtract(
                        price.getDeliverPrice()).multiply((BigDecimal) map.get("divideRatio")));
            }

            // 获取推荐产品
            map.put("recommends", specialtyService.getSpecialtiesForRecommendSpecialty(s.getSpecialtyId()));
            HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(s.getId(), null);
            map.put("promotion", singleitemPromotion);

            // 获取标签
            List<HyLabel> hyLabels = hyLabelService.getHyLableListBySpecialtyId(s.getSpecialtyId());
            for (HyLabel label : hyLabels) {
                if(!label.getIsActive() || !hySpecialtyLabelService.isMarked(label.getId(), s.getSpecialtyId(), true)){
                    hyLabels.remove(label);
                }
            }
            map.put("labels", hyLabels);

            // 找销量
            if (hasSolds.containsKey(specialty.getId())) {
                map.put("hasSold", hasSolds.get(specialty.getId()));
            } else {
                Integer hasSold = specialty.getBaseSaleNumber();

                List<SpecialtySpecification> specialtySpecificationList = this.getAllSpecification(specialty.getId());
                for (SpecialtySpecification specification : specialtySpecificationList) {
                    hasSold += specification.getSaleNumber() * specification.getHasSold();
                }
                map.put("hasSold", hasSold);
                hasSolds.put(specialty.getId(), hasSold);
            }

            rows.add(map);
        }
        return rows;
    }

    private List<Map<String, Object>> filterSpecificationPrice(List<Map<String, Object>> srows) {
        // 特产基本信息Map
        Map<Object, Map<String, Object>> spMap = new HashMap<>();
        // 特产销量Map
        Map<Object, Integer> saMap = new HashMap<>();
        for (Map<String, Object> sp : srows) {
            // 最低价格
            if (spMap.containsKey(sp.get("specialty"))){
                Map<String, Object> tmp = spMap.get(sp.get("specialty"));
                BigDecimal spPrice = (BigDecimal) sp.get("pPrice");
                BigDecimal tmpPrice = (BigDecimal) tmp.get("pPrice");
                if (spPrice.compareTo(tmpPrice) < 0) {
                    spMap.put(sp.get("specialty"), sp);
                }
            } else {
                spMap.put(sp.get("specialty"), sp);
            }
            // 总销量
            if (saMap.containsKey(sp.get("specialty"))) {
                Integer saleNumber = saMap.get(sp.get("specialty"));
                SpecialtySpecification specification = (SpecialtySpecification) sp.get("specification");
                saMap.put(sp.get("specialty"), saleNumber + specification.getHasSold() * specification.getSaleNumber());
            } else {
                Specialty specialty = (Specialty) sp.get("specialty");
                SpecialtySpecification specification = (SpecialtySpecification) sp.get("specification");
                saMap.put(sp.get("specialty"), specialty.getBaseSaleNumber() + specification.getHasSold() * specification.getSaleNumber());
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Map.Entry<Object, Map<String, Object>> entry : spMap.entrySet()) {
            Map<String, Object> value = entry.getValue();
            Object key = entry.getKey();
            if(!value.containsKey("hasSold")){
                value.put("hasSold", saMap.get(key));
            }
            list.add(value);
        }
        return list;
    }
}
