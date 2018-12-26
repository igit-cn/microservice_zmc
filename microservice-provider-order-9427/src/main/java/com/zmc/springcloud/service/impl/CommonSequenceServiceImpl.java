package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;
import com.zmc.springcloud.mapper.CommonSequenceMapper;
import com.zmc.springcloud.service.CommonSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class CommonSequenceServiceImpl implements CommonSequenceService{
    @Autowired
    private CommonSequenceMapper commmonSequenceMapper;


    @Override
    public Long getValue(SequenceTypeEnum sequenceTypeEnum) throws Exception {
       return commmonSequenceMapper.findValueByType(sequenceTypeEnum.ordinal());
    }

    @Override
    public void updateValue(SequenceTypeEnum sequenceTypeEnum, Long newValue) throws Exception{
        commmonSequenceMapper.updateValue(sequenceTypeEnum.ordinal(), newValue);
    }
}
