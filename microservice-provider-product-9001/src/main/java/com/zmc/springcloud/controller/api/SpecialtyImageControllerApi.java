package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.SpecialtyImage;
import com.zmc.springcloud.service.SpecialtyImageService;
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
public class SpecialtyImageControllerApi {
    @Autowired
    private SpecialtyImageService specialtyImageService;

    @RequestMapping("/product/image/{specialtyId}")
    public List<SpecialtyImage> getSpecialtyImageListBySpecialtyId(@PathVariable Long specialtyId) throws Exception{
        return specialtyImageService.getSpecialtyImageList(specialtyId);
    }
}
