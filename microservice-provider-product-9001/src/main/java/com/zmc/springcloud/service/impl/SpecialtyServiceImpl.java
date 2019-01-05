package com.zmc.springcloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;
import com.zmc.springcloud.feignclient.common.CommonSequenceFeignClient;
import com.zmc.springcloud.feignclient.express.HyVinboundFeignClient;
import com.zmc.springcloud.feignclient.login.HyAdminFeignClient;
import com.zmc.springcloud.feignclient.common.HyAreaFeignClient;
import com.zmc.springcloud.feignclient.supplier.ProviderFeignClient;
import com.zmc.springcloud.mapper.SpecialtyMapper;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
@Service

public class SpecialtyServiceImpl implements SpecialtyService{
    @Autowired
    private HyAdminFeignClient hyAdminFeignClient;

    @Autowired
    private CommonSequenceFeignClient commonSequenceFeignClient;

    @Autowired
    private HyAreaFeignClient hyAreaFeignClient;

    @Autowired
    private ProviderFeignClient providerFeignClient;

    @Autowired
    private HyVinboundFeignClient hyVinboundFeignClient;

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Autowired
    private SpecialtyAppraiseService specialtyAppraiseService;

    @Autowired
    private SpecialtySpecificationService specialtySpecificationService;

    @Autowired
    private SpecialtyImageService specialtyImageService;

    @Autowired
    private SpecialtyCategoryService specialtyCategoryService;

    @Autowired
    private SpecialtyPriceService specialtyPriceService;

    @Autowired
    private BusinessBannerService businessBannerService;

    @Override
    public Specialty getSpecialtyById(Long id) {
        return specialtyMapper.findById(id);
    }

    @Override
    public HashMap<String, Object> getProductList(Integer page, Integer rows, Long providerId, String name, Long categoryId, String userNameInSession) throws Exception {
        PageHelper.startPage(page, rows);
        List<Specialty> list = specialtyMapper.findListSpecialty(providerId, name, categoryId, userNameInSession);
        PageInfo pageInfo = new PageInfo(list);
        for (Specialty s : list) {
            Long count = specialtyAppraiseService.count(s.getId());
            s.setAppraiseCount(count);

            // 不管权限范围 直接赋予编辑权限
            s.setPrivilege("edit");
        }
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("rows", list);
        obj.put("pageNumber", page);
        obj.put("pageSize", rows);
        obj.put("total", pageInfo.getTotal());
        obj.put("totalPages", pageInfo.getPages());
        return obj;
    }

    @Override
    public void addProduct(JSONObject jsonObject, String usernameInSession) throws Exception {
        String name = jsonObject.getString("name");
        String brand = jsonObject.getString("brand");
        String originalPlace = jsonObject.getString("originalPlace");
        String productionLicenseNumber = jsonObject.getString("productionLicenseNumber");
        String storageMethod = jsonObject.getString("storageMethod");
        String descriptions = jsonObject.getString("descriptions");

        JSONObject category = jsonObject.getJSONObject("category");
        JSONObject provider = jsonObject.getJSONObject("provider");
        JSONObject icon = jsonObject.getJSONObject("icon");

        JSONArray specifications = jsonObject.getJSONArray("specifications");
        JSONArray images = jsonObject.getJSONArray("images");

        Long categoryId = category.getLong("id");
        Long providerId = provider.getLong("id");

        // 获取序列号
        Long value = commonSequenceFeignClient.findValueByType(SequenceTypeEnum.specialtySn.ordinal()) + 1;
        // 更新序列号
        commonSequenceFeignClient.updateValue(SequenceTypeEnum.specialtySn.ordinal(), value);
        String code = String.format("%05d", categoryId) + String.format("%05d", value);

        HyAdmin hyAdmin = hyAdminFeignClient.getHyAdminByUserName(usernameInSession);

        //新建产品 为了获取自增主键 传递实体而不是参数列表
        Specialty specialty = new Specialty();
        specialty.setBrand(brand);
        specialty.setCode(code);
        specialty.setCreateTime(new Date());
        specialty.setDescriptions(descriptions);
        // 没有添加库存,先默认不上架
        specialty.setIsActive(false);
        // numberOffFreeGift默认值为0
        specialty.setNumberOfFreeGift(0);
        specialty.setCategoryId(categoryId);
        specialty.setName(name);
        specialty.setOriginalPlace(originalPlace);
        specialty.setProviderId(providerId);
        specialty.setStorageMethod(storageMethod);
        // 没有添加库存 saleState先默认为0
        specialty.setSaleState(0);
        specialty.setProductionLicenseNumber(productionLicenseNumber);
        specialty.setCreator(usernameInSession);
        specialty.setCreatorName(hyAdmin.getName());
        // isBanner先默认为不在banner展示
        specialty.setIsBanner(false);
        // 没有添加库存 baseSaleNumber默认为0
        specialty.setBaseSaleNumber(0);
        specialtyMapper.insertProduct(specialty);
        // insert后可获取到自增主键
        Long specialtyId = specialty.getId();

        // 保存产品规格
        List<SpecialtySpecification> specialtySpecificationList = new LinkedList<>();
        for (int i = 0; i < specifications.size(); i++) {
            JSONObject jsonObj = specifications.getJSONObject(i);
            SpecialtySpecification s = new SpecialtySpecification();
            s.setSpecialtyId(specialtyId);
            s.setCreateTime(new Date());
            // 新建产品时某一规格的产品已售数量初始化为0
            s.setHasSold(0);
            // isActive初始化为true
            s.setIsActive(true);
            s.setSpecification(jsonObj.getString("specification"));
            s.setCreatorName(hyAdmin.getName());
            s.setPid(jsonObj.getLong("parent"));
            s.setSaleNumber(jsonObj.getInteger("saleNumber"));
            // 基础库存初始化为0
            s.setBaseInbound(0);
            specialtySpecificationList.add(s);
        }
        specialtySpecificationService.batchInsert(specialtySpecificationList);

        // 保存产品图片
        List<SpecialtyImage> specialtyImageList = new ArrayList<>();
        for(int i = 0; i< images.size(); i++){
            JSONObject jsonObj = images.getJSONObject(i);
            SpecialtyImage s = new SpecialtyImage();
            s.setSpecialtyId(specialtyId);
            s.setThumbnailPath(jsonObj.getString("thumbnailPath"));
            s.setMediumPath(jsonObj.getString("mediumPath"));
            s.setLargePath(jsonObj.getString("largePath"));
            s.setSourcePath(jsonObj.getString("sourcePath"));
            // 产品图片, 不是logo
            s.setIsLogo(false);
        }
        specialtyImageService.batchInsert(specialtyImageList);

        // 保存产品logo
        SpecialtyImage s = new SpecialtyImage();
        s.setSpecialtyId(specialtyId);
        s.setThumbnailPath(icon.getString("thumbnailPath"));
        s.setMediumPath(icon.getString("mediumPath"));
        s.setLargePath(icon.getString("largePath"));
        s.setSourcePath(icon.getString("sourcePath"));
        // 是logo
        s.setIsLogo(true);
        specialtyImageService.insert(s);
    }

    @Override
    public List<Map<String, Object>> getSpecialtiesForRecommendSpecialty(Long id) throws Exception {
        List<Map<String, Object>> listMap = new ArrayList<>();
        List<Specialty> recommendList = specialtyMapper.getSpecialtiesForRecommendSpecialty(id);
        for (Specialty s : recommendList) {
            Map<String, Object> m = new HashMap<>();
            m.put("specialtyid", s.getId());
            m.put("specialtyname", s.getName());
            listMap.add(m);
        }
        return listMap;
    }

    @Override
    public HashMap<String, Object> getSpecialtyDetail(Long id) throws Exception{
        HashMap<String, Object> obj = new HashMap<>();
        Specialty specialty = specialtyMapper.findById(id);
        // 没有使用级联 以HashMap形成级联
        obj.put("appraiseCount", specialty.getAppraiseCount());
        Long areaId = specialty.getAreaId();
        HyArea hyArea = null;
        if(areaId != null){
             hyArea =  hyAreaFeignClient.getHyAreaById(areaId);
        }
        obj.put("area", hyArea);
        obj.put("baseSaleNumber", specialty.getBaseSaleNumber());
        obj.put("brand", specialty.getBrand());
        // TODO 这样写基于分区最多只有两级
        SpecialtyCategory specialtyCategory = specialtyCategoryService.getSpecialtyCategoryById(specialty.getCategoryId());
        if(specialtyCategory.getPid() != null){
            SpecialtyCategory specialtyCategoryParent = specialtyCategoryService.getSpecialtyCategoryById(specialtyCategory.getPid());
            specialtyCategory.setParent(specialtyCategoryParent);
        }
        obj.put("category", specialtyCategory);
        obj.put("code", specialty.getCode());
        obj.put("couponAvailable", specialty.getCouponAvailable());
        obj.put("createTime", specialty.getCreateTime());
        obj.put("creator", specialty.getCreator());
        obj.put("creatorName", specialty.getCreatorName());
        obj.put("deliverType", specialty.getDeliverType());
        obj.put("descriptions", specialty.getDescriptions());
        obj.put("id", id);
        obj.put("icon", null);
        List<SpecialtyImage> list = specialtyImageService.getSpecialtyImageList(id);
        List<SpecialtyImage> images = new ArrayList<>();
        for (SpecialtyImage image : list) {
            if(image.getIsLogo()){
                obj.put("icon", image);
            }else {
                images.add(image);
            }
        }
        obj.put("images", images);
        obj.put("isActive", specialty.getIsActive());
        obj.put("isBanner", specialty.getIsBanner());
        obj.put("isFreeGift", specialty.getIsFreeGift());
        obj.put("isRecommend", specialty.getIsRecommend());
        obj.put("isReturnable", specialty.getIsReturnable());
        obj.put("modifierName", specialty.getModifierName());
        obj.put("modifyTime", specialty.getModifyTime());
        obj.put("name", specialty.getName());
        obj.put("numberOfFreeGift", specialty.getNumberOfFreeGift());
        obj.put("orders", specialty.getOrders());
        obj.put("originalPlace", specialty.getOriginalPlace());
        obj.put("privilege", specialty.getPrivilege());
        obj.put("productionLicenseNumber", specialty.getProductionLicenseNumber());
        Provider provider = providerFeignClient.getProviderById(specialty.getProviderId());
        obj.put("provider", provider);
        obj.put("putoffTime", specialty.getPutoffTime());
        obj.put("putonTime", specialty.getPutonTime());
        obj.put("recommendOrder", specialty.getRecommendOrder());
        obj.put("saleState", specialty.getSaleState());
        obj.put("shipType", specialty.getShipType());
        List<Specialty> recommendList = specialtyMapper.getSpecialtiesForRecommendSpecialty(id);
        obj.put("specialtiesForRecommendSpecialtyId", recommendList);
        List<SpecialtySpecification> filterSpecifications = new ArrayList<>();
        List<SpecialtySpecification> specialtySpecificationList = specialtySpecificationService.getAllSpecification(id);
        for (SpecialtySpecification spe : specialtySpecificationList) {
            SpecialtyPrice specialtyPrice = specialtyPriceService.findList(spe.getSpecialtyId(), true);
            if(specialtyPrice != null){
                spe.setCostPrice(specialtyPrice.getCostPrice());
                spe.setMarketPrice(specialtyPrice.getMarketPrice());
                spe.setPlatformPrice(specialtyPrice.getPlatformPrice());
                spe.setBusinessPersonDivide(specialtyPrice.getBusinessPersonDivide());
                spe.setStoreDivide(specialtyPrice.getStoreDivide());
                spe.setExterStoreDivide(specialtyPrice.getExterStoreDivide());
                spe.setDeliverPrice(specialtyPrice.getDeliverPrice());
                if (spe.getPid().equals(Long.valueOf(0))) {
                    HyVinbound hyVinbound = hyVinboundFeignClient.getHyVinboundBySpecificationId(spe.getId());
                    if(hyVinbound != null){
                        spe.setVInboundNumber(hyVinbound.getVinboundNumber());
                    }
                }
            }
            filterSpecifications.add(spe);
        }
        obj.put("specifications", filterSpecifications);
        obj.put("storageMethod", specialty.getStorageMethod());
        return obj;
    }

    @Override
    public void modifyProductByQudaoXiaoShou(JSONObject paylaod, String usernameInSession) throws Exception{
        /*操作的用户*/
        HyAdmin hyAdmin = hyAdminFeignClient.getHyAdminByUserName(usernameInSession);

        /*paylaod解析*/
        JSONObject specialtyJSONObject = paylaod.getJSONObject("specialty");
        JSONArray recommendIds = paylaod.getJSONArray("recommendIds");
        /* specialtyJSONObject中的各子项*/
        Boolean couponAvailable = specialtyJSONObject.getBoolean("couponAvailable");
        Boolean isRecommend = specialtyJSONObject.getBoolean("isRecommend");
        Integer recommendOrder = specialtyJSONObject.getInteger("recommendOrder");
        Integer saleState = specialtyJSONObject.getInteger("saleState");
        Boolean isActive = specialtyJSONObject.getBoolean("isActive");
        Boolean isReturnable = specialtyJSONObject.getBoolean("isReturnable");
        Integer deliverType = specialtyJSONObject.getInteger("deliverType");
        // TODO 需要将String的字符串转为时间格式
        String putonTime = specialtyJSONObject.getString("putonTime");
        String putoffTime = specialtyJSONObject.getString("putoffTime");
        Boolean isBanner = specialtyJSONObject.getBoolean("isBanner");
        Long orders = specialtyJSONObject.getLong("orders");
        Integer baseSaleNumber = specialtyJSONObject.getInteger("baseSaleNumber");
        Integer shipType = specialtyJSONObject.getInteger("shipType");
        Long areaId = specialtyJSONObject.getJSONObject("area").getLong("id");
        Long id = specialtyJSONObject.getLong("id");
        // 产品规格的JSONArray数组
        JSONArray specifications = specialtyJSONObject.getJSONArray("specifications");

        Specialty oldSpecialty = specialtyMapper.findById(id);
        oldSpecialty.setOrders(orders);
        oldSpecialty.setIsRecommend(isRecommend);
        oldSpecialty.setIsReturnable(isReturnable);
        oldSpecialty.setSaleState(saleState);
        oldSpecialty.setPutonTime(DateUtil.stringToDate(putonTime));
        oldSpecialty.setPutoffTime(DateUtil.stringToDate(putoffTime));
        oldSpecialty.setCouponAvailable(couponAvailable);
        oldSpecialty.setDeliverType(deliverType);
        oldSpecialty.setShipType(shipType);
        oldSpecialty.setIsActive(isActive);
        oldSpecialty.setRecommendOrder(recommendOrder);
        oldSpecialty.setBaseSaleNumber(baseSaleNumber);

        // 修改规格
        if(specifications != null){
            for (int i = 0; i < specifications.size(); i++) {
                JSONObject jsonObject = specifications.getJSONObject(i);
                String specificationName = jsonObject.getString("specification");
                Long parent = jsonObject.getLong("parent");
                BigDecimal deliverPrice = jsonObject.getBigDecimal("deliverPrice");
                Integer vInboundNumber = jsonObject.getInteger("vInboundNumber");
                Integer saleNumber = jsonObject.getInteger("saleNumber");
                BigDecimal marketPrice = jsonObject.getBigDecimal("marketPrice");
                BigDecimal costPrice = jsonObject.getBigDecimal("costPrice");
                BigDecimal platformPrice = jsonObject.getBigDecimal("platformPrice");
                Integer hasSold = jsonObject.getInteger("hasSold");
                Boolean specificationsIsActive = jsonObject.getBoolean("isActive");
                Boolean isFreeGift = jsonObject.getBoolean("isFreeGift");
                Long specificationsId = jsonObject.getLong("id");
                BigDecimal storeDivide = jsonObject.getBigDecimal("storeDivide");
                BigDecimal exterStoreDivide = jsonObject.getBigDecimal("exterStoreDivide");
                BigDecimal businessPersonDivide = jsonObject.getBigDecimal("businessPersonDivide");

                SpecialtySpecification spe = specialtySpecificationService.findById(specificationsId);
                // TODO 前台没有传递baseInbound
                Integer baseInbound = 0;
                if(spe.getBaseInbound() == null){
                    spe.setBaseInbound(0);
                }

                // 只有父规格才考虑虚拟库存
                if(spe.getPid().equals(Long.valueOf(0))){
                    HyVinbound hyVinbound = hyVinboundFeignClient.getHyVinboundBySpecificationId(spe.getId());;
                    // 渠道销售修改时才考虑新建虚拟库存或者修改虚拟库存
                    if(hyVinbound != null){
                        // 修改虚拟库存的存量
                        if(hyVinbound.getVinboundNumber().equals(vInboundNumber)){
                            //修改基本库存*wayne*
                            baseInbound = spe.getBaseInbound() - hyVinbound.getVinboundNumber() + vInboundNumber;
                            hyVinbound.setVinboundNumber(vInboundNumber);
                            hyVinboundFeignClient.updateHyVinbound(hyVinbound);
                        }else{
                            baseInbound = spe.getBaseInbound();
                        }
                    }else{
                        //修改基本库存*wayne*
                        baseInbound = spe.getBaseInbound() +  vInboundNumber;
                        hyVinbound = new HyVinbound();
                        hyVinbound.setSpecialtySpecificationId(spe.getId());
                        hyVinbound.setVinboundNumber(vInboundNumber);
                        hyVinboundFeignClient.addHyVinbound(hyVinbound);
                    }
                }

                spe.setCostPrice(costPrice);
                spe.setPlatformPrice(platformPrice);
                spe.setMarketPrice(marketPrice);
                spe.setExterStoreDivide(exterStoreDivide);
                spe.setStoreDivide(storeDivide);
                spe.setBusinessPersonDivide(businessPersonDivide);
                spe.setDeliverPrice(deliverPrice);
                spe.setIsActive(specificationsIsActive);
                spe.setIsFreeGift(isFreeGift);
                spe.setVInboundNumber(vInboundNumber);
                spe.setModifyTime(new Date());
                // TODO 此处存疑 原工程中,这里应该前端没有传递baseInbound参数
                spe.setBaseInbound(baseInbound);

                if(spe.getIsFreeGift() != null && isFreeGift != null){
                    if(spe.getIsFreeGift() && !isFreeGift){
                        // TODO 此处存疑 原工程中,这里应该前端没有传递numberOfFreeGift参数
                    }else if(!spe.getIsFreeGift() && isFreeGift){
                        int no = oldSpecialty.getNumberOfFreeGift();
                        no++;
                        oldSpecialty.setNumberOfFreeGift(no);
                    }
                }
                spe.setSpecification(specificationName);
                spe.setModifierName(usernameInSession);
                // 更新规格
                specialtySpecificationService.update(spe);

                // 将原工程中的修改价格 合并到修改规格中
                SpecialtyPrice oldprice = specialtyPriceService.findList(spe.getId(), true);
                // 之前hy_specialty_price已有数据
                if(oldprice != null){
                    // 页面传来的数据和之前的数据不同
                    if(oldprice.getMarketPrice().compareTo(marketPrice) !=0 ||
                            oldprice.getCostPrice().compareTo(costPrice) !=0     ||
                            oldprice.getPlatformPrice().compareTo(platformPrice) != 0 ||
                            oldprice.getStoreDivide().compareTo(storeDivide) != 0 ||
                            oldprice.getExterStoreDivide().compareTo(exterStoreDivide) != 0 ||
                            oldprice.getBusinessPersonDivide().compareTo(businessPersonDivide) != 0 ||
                            oldprice.getDeliverPrice().compareTo(deliverPrice) != 0){
                        // 保存新的数据
                        SpecialtyPrice newprice = new SpecialtyPrice();
                        newprice.setSpecialtyId(oldSpecialty.getId());
                        newprice.setSpecificationId(spe.getId());
                        newprice.setMarketPrice(marketPrice);
                        newprice.setCostPrice(costPrice);
                        newprice.setIsActive(true);
                        newprice.setCreateTime(new Date());
                        newprice.setCreatorName(usernameInSession);
                        newprice.setStoreDivide(storeDivide);
                        newprice.setExterStoreDivide(exterStoreDivide);
                        newprice.setBusinessPersonDivide(businessPersonDivide);
                        newprice.setDeliverPrice(deliverPrice);
                        specialtyPriceService.save(newprice);

                        // 将之前数据的isActive等修改
                        specialtyPriceService.updateSpecialtyPrice(oldprice.getId());
                    }
                }else{
                    // 之前在hy_specialty_price没有相应的数据
                    SpecialtyPrice newprice = new SpecialtyPrice();
                    newprice.setSpecialtyId(oldSpecialty.getId());
                    newprice.setSpecificationId(spe.getId());
                    newprice.setMarketPrice(marketPrice);
                    newprice.setCostPrice(costPrice);
                    newprice.setIsActive(true);
                    newprice.setCreateTime(new Date());
                    newprice.setCreatorName(usernameInSession);
                    newprice.setStoreDivide(storeDivide);
                    newprice.setExterStoreDivide(exterStoreDivide);
                    newprice.setBusinessPersonDivide(businessPersonDivide);
                    newprice.setDeliverPrice(deliverPrice);
                    specialtyPriceService.save(newprice);
                }
            }
        }

        // 清空原来的特产推荐
        specialtyMapper.clearSpecialtiesForRecommend(oldSpecialty.getId());
        // 添加前台传递的规格
        for (int i = 0; i < recommendIds.size(); i++) {
            Long recommendSpecialtyId = recommendIds.getLong(i);
            specialtyMapper.insertSpecialtiesForRecommend(oldSpecialty.getId(), recommendSpecialtyId);
        }

        oldSpecialty.setAreaId(areaId);

        if (oldSpecialty.getIsBanner() && !isBanner) {
            // 若为由在Banner显示改为不显示
            oldSpecialty.setIsBanner(isBanner);
            businessBannerService.updateBannerState(oldSpecialty.getId(), BusinessBanner.BannerType.产品.ordinal(), false, null, null);
        } else if (!oldSpecialty.getIsBanner() && isBanner) {
            // 若为由不在Banner显示改为显示
            BusinessBanner businessBanner = businessBannerService.getBusinessBanner(oldSpecialty.getId(), BusinessBanner.BannerType.产品.ordinal());
            if (businessBanner != null) {
                businessBannerService.updateBannerState(oldSpecialty.getId(), BusinessBanner.BannerType.产品.ordinal(), true, oldSpecialty.getPutonTime(), oldSpecialty.getPutoffTime());
            } else {
                BusinessBanner banner = new BusinessBanner();
                banner.setTitle(oldSpecialty.getName());
                banner.setType(BusinessBanner.BannerType.产品);
                List<SpecialtyImage> images = specialtyImageService.getSpecialtyImageList(oldSpecialty.getId());
                for (SpecialtyImage image : images) {
                    if (image.getIsLogo()) {
                        banner.setImg(image.getSourcePath());
                    }
                }
                banner.setCreator(usernameInSession);
                banner.setTargetId(oldSpecialty.getId());
                banner.setStartTime(oldSpecialty.getPutonTime());
                banner.setEndTime(oldSpecialty.getPutoffTime());
                banner.setState(true);
                businessBannerService.saveBusinessBanner(banner);
            }
        }

        //如果将父规格设为无效了，则所有对应的自规格均设为无效
        List<SpecialtySpecification> fuSpecifications = new ArrayList<>();
//        for(SpecialtySpecification specification:oldSpecialty.getSpecifications()){
//
//        }

        // 若上架时间小于等于当前时间，立即上架
        if (oldSpecialty.getPutonTime() != null && oldSpecialty.getPutonTime().compareTo(new Date()) <= 0) {
            oldSpecialty.setSaleState(1);
        } else if (oldSpecialty.getPutonTime() != null && oldSpecialty.getPutonTime().compareTo(new Date()) > 0) {
            oldSpecialty.setSaleState(0);
        }

        // 更新特产
        specialtyMapper.update(oldSpecialty);
    }

}
