package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.CommonSequence;
import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;
import com.zmc.springcloud.mapper.CommonSequenceMapper;
import com.zmc.springcloud.service.CommonSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String getCode(SequenceTypeEnum type, Long param) {
        // 如果是生成订单编号
        if (SequenceTypeEnum.orderSn.equals(type)) {
            // 获取序列号
            Long value = commmonSequenceMapper.findValueByType(CommonSequence.SequenceTypeEnum.businessOrderSuq.ordinal()) + 1;
            // 更新序列号
            commmonSequenceMapper.updateValue(CommonSequence.SequenceTypeEnum.businessOrderSuq.ordinal(), value);
            // 生成订单编号
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String nowaday = sdf.format(new Date());
            // SN至少为8位,不足补零
            String code = nowaday + String.format("%08d", value);
            return code;
        }
        if (SequenceTypeEnum.specialtySn.equals(type)) {
            // 获取序列号
            Long value = commmonSequenceMapper.findValueByType(SequenceTypeEnum.specialtySn.ordinal()) + 1;
            // 更新序列号
            commmonSequenceMapper.updateValue(SequenceTypeEnum.specialtySn.ordinal(), value);
            String code = String.format("%05d", param) + String.format("%05d", value);
            return code;
        }
        return null;
    }

}
