package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyFullDiscount;
import com.zmc.springcloud.service.HyFullDiscountService;
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
public class HyFullDiscountControllerApi {
    @Autowired
    private HyFullDiscountService hyFullDiscountService;

    @RequestMapping(value = "/hyfulldiscount/list/{promotionId}")
    public List<HyFullDiscount> getHyFullDiscountListByPromotionId(@PathVariable Long promotionId) throws Exception{
        return hyFullDiscountService.getHyFullDiscountListByPromotionId(promotionId);
    }
}
