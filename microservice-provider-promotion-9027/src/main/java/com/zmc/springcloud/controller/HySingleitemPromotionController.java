package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import com.zmc.springcloud.service.HySingleitemPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/5.
 *
 * @author xyy
 */
@RestController
public class HySingleitemPromotionController {
    @Autowired
    private HySingleitemPromotionService hySingleitemPromotionService;

    @RequestMapping("/promotion/singleitem/")
    public HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId) throws Exception {
        return hySingleitemPromotionService.getValidSingleitemPromotion(specialtySpecificationId, promotionId);
    }

    @RequestMapping("/promotion/singleitem/update")
    public void updateSingleItemPromotion(HySingleitemPromotion singleitemPromotion) throws Exception{
        hySingleitemPromotionService.updatePromotion(singleitemPromotion);
    }
}
