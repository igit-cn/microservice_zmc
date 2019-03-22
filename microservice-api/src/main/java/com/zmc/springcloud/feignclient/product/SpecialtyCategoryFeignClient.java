package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.utils.ArrayHandler;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface SpecialtyCategoryFeignClient {

    @GetMapping("/specialtycategory/superlist")
    List<SpecialtyCategory> getSpecialtyCategorySuperList();

    @GetMapping("/product/sub_list_for_recommend/{size}")
    List<Map<String, Object>> getSubListForRecommendBySize(@PathVariable("size") Integer size);

    @GetMapping("/product/sub_list_by_category_id_and_size")
    List<Map<String, Object>> getSubListByCategoryIdAndSize(@RequestParam("catgoryId") Long categoryId, @RequestParam("size") Integer size);

}
