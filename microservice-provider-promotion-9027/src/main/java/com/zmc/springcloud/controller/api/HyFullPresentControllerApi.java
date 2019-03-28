package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyFullPresent;
import com.zmc.springcloud.service.HyFullPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@RestController
public class HyFullPresentControllerApi {
    @Autowired
    private HyFullPresentService hyFullPresentService;

    @RequestMapping(value = "/hyfullpresent/list/{promotionId}")
    public List<HyFullPresent> getHyFullPresentListByPromotionId(@PathVariable("promotionId") Long promotionId) throws Exception{
        return hyFullPresentService.getHyFullPresentListByPromotionId(promotionId);
    }
}
