package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.WeDivideModel;
import com.zmc.springcloud.mapper.WeDivideModelMapper;
import com.zmc.springcloud.service.WeDivideModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Service
public class WeDivideModelServiceImpl implements WeDivideModelService{
    @Autowired
    private WeDivideModelMapper weDivideModelMapper;

    @Override
    public List<WeDivideModel> getWeDivideModelListByModelTypeAndIsValid(String modelType, Boolean isValid) throws Exception {
        return weDivideModelMapper.findWeDivideModelListByModelTypeAndIsValid(modelType, isValid);
    }
}
