package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface CommonSequenceService {
    Long getValue(Integer type) throws Exception;
    void updateValue(Integer type, Long newValue) throws Exception;
}
