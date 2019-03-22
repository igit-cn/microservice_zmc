package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.service.SpecialtyCategoryService;
import com.zmc.springcloud.service.SpecialtyService;
import com.zmc.springcloud.utils.ArrayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@RestController
public class SpecialtyCategoryControllerApi {
    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialtyCategoryService specialtyCategoryService;

    @GetMapping("/specialtycategory/superlist")
    public List<SpecialtyCategory> getSpecialtyCategorySuperList() {
        return specialtyCategoryService.getSpecialtyCategorySuperList();
    }

    @GetMapping("/product/sub_list_for_recommend/{size}")
    public List<Map<String, Object>> getSubListForRecommendBySize(@PathVariable("size") Integer size) {
        List<Object[]> objects = specialtyService.getSubListForRecommendBySize(size);
        List<Map<String, Object>> maps = new ArrayList<>();

        for (Object[] object : objects) {
            maps.add(ArrayHandler.toSpecialtyBaseInfoMap(object));
        }
        return maps;
    }

    @GetMapping("/product/sub_list_by_category_id_and_size")
    public List<Map<String, Object>> getSubListByCategoryIdAndSize(@RequestParam("catgoryId") Long categoryId, @RequestParam("size") Integer size) throws Exception {
        List<Object[]> objects = specialtyCategoryService.getSubListByCategoryIdAndSize(categoryId, size);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Object[] object : objects) {
            maps.add(ArrayHandler.toSpecialtyBaseInfoMap(object));
        }
        return maps;
    }
}
