package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.WeDivideModel;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
public interface WeDivideModelService {
    /** 根据提成模型类型和isValid获取WeDivideModel的列表*/
    List<WeDivideModel> getWeDivideModelListByModelTypeAndIsValid(String modelType, Boolean isValid) throws Exception;
}
