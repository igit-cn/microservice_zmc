package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.HyPromotion;
import com.zmc.springcloud.service.HyPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@RestController
public class PromotionController {
    @Autowired
    private HyPromotionService hyPromotionService;

    @RequestMapping("/promotion/{id}")
    public HyPromotion getHyPromotionById(@PathVariable("id")  Long id) throws Exception{
        return hyPromotionService.getHyPromotionById(id);
    }


}
