package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyFullSubstract;
import com.zmc.springcloud.service.HyFullSubstractService;
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
public class HyFullSubstractControllerApi {
    @Autowired
    private HyFullSubstractService hyFullSubstractService;

    @RequestMapping(value = "/hyfullsubstract/list/{promotionId}")
    public List<HyFullSubstract> getHyFullSubstractListByPromotionId(@PathVariable("promotionId") Long promotionId) throws Exception{
        return hyFullSubstractService.getHyFullSubstractListByPromotionId(promotionId);
    }
}