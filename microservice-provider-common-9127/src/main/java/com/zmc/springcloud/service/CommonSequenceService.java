package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface CommonSequenceService {
    /** 根据类型,生成不同的编号  TODO 使用策略模式对生成序列号重写 */
    String getCode(SequenceTypeEnum type, Long param);
}
