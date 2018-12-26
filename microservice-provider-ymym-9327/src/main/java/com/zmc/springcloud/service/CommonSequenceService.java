package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CommonSequence;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface CommonSequenceService {
    Long getValue(CommonSequence.SequenceTypeEnum sequenceTypeEnum) throws Exception;
    void updateValue(CommonSequence.SequenceTypeEnum sequenceTypeEnum, Long newValue)throws Exception;
}
