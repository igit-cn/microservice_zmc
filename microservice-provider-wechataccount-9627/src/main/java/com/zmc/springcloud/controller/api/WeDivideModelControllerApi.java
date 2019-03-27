package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.WeDivideModel;
import com.zmc.springcloud.service.WeDivideModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@RestController
public class WeDivideModelControllerApi {
    @Autowired
    private WeDivideModelService weDivideModelService;

    /** 根据提成模型类型和isValid获取WeDivideModel的列表*/
    @GetMapping("/wedividemodel/list")
    public List<WeDivideModel> getWeDivideModelListByModelTypeAndIsValid(String modelType, Boolean isValid) throws Exception{
        return weDivideModelService.getWeDivideModelListByModelTypeAndIsValid(modelType, isValid);
    }
}
