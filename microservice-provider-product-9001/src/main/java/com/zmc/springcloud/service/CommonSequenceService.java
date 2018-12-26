package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface CommonSequenceService {
    Long getValue(SequenceTypeEnum sequenceTypeEnum) throws Exception;
    void updateValue(SequenceTypeEnum sequenceTypeEnum, Long newValue) throws Exception;
}
