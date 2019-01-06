package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import com.zmc.springcloud.service.HyGroupitemPromotionDetailService;
import com.zmc.springcloud.service.HyGroupitemPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class HyGroupitemPromotionController {
    @Autowired
    private HyGroupitemPromotionService hyGroupitemPromotionService;

    @Autowired
    private HyGroupitemPromotionDetailService hyGroupitemPromotionDetailService;

    @RequestMapping(value = "/group/item/promotion/{id}")
    public HyGroupitemPromotion getHyGroupitemPromotionById(@PathVariable Long id) throws Exception{
        return hyGroupitemPromotionService.getHyGroupitemPromotionById(id);
    }

    @RequestMapping(value = "/group/item/promotion/detail/{id}")
    public List<HyGroupitemPromotionDetail> getHyGroupitemPromotionDetailList(@PathVariable Long id) throws Exception{
        return hyGroupitemPromotionDetailService.getHyGroupitemPromotionDetailList(id);
    }

    @RequestMapping(value = "/group/item/promotion/update")
    public void updateGroupitemPromotion(HyGroupitemPromotion hyGroupitemPromotion){
        hyGroupitemPromotionService.updateHyGroupitemPromotion(hyGroupitemPromotion);
    }
}
