package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyPromotionPic;
import com.zmc.springcloud.service.HyPromotionPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@RestController
public class PromotionPicControllerApi {
    @Autowired
    private HyPromotionPicService hyPromotionPicService;

    @RequestMapping(value = "/promotion/pic/{promotionId}")
    public List<HyPromotionPic> getHyPromotionPicListByPromotionId(@PathVariable Long promotionId) throws Exception{
        return hyPromotionPicService.getHyPromotionPicListByPromotionId(promotionId);
    }
}
