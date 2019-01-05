package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.entity.SpecialtySpecification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface SpecialtyFeignClient {
    @RequestMapping(value = "/product/specialty/{id}")
    Specialty getSpecialtyById(@PathVariable Long id);

    @RequestMapping(value = "/product/specification/{id}")
    SpecialtySpecification getSpecialtySpecificationById(@PathVariable Long id);

    @RequestMapping(value = "/group/promotion/{id}")
    HyGroupitemPromotion getHyGroupitemPromotionById(@PathVariable Long id);

    @RequestMapping(value = "/group/promotion/detail/{id}")
    List<HyGroupitemPromotionDetail> getHyGroupitemPromotionDetailList(@PathVariable Long id);
}
