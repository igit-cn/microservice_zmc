package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.SpecialtyImage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface SpecialtyImageFeignClient {
    @RequestMapping("/product/image/{specialtyId}")
    List<SpecialtyImage> getSpecialtyImageListBySpecialtyId(@PathVariable("specialtyId") Long specialtyId);
}
