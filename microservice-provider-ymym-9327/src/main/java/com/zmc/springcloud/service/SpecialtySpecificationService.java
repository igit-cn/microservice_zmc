package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface SpecialtySpecificationService {
    boolean isBaseInboundEnough(List<Map<String, Object>> orderItems) throws Exception;

    SpecialtySpecification find(Long specialtySpecificationId)throws Exception;
    /** 更新规格的基础库存和销量*/
    void updateBaseInboundAndHasSold(BusinessOrderItem businessOrderItem, Boolean isSale) throws Exception;
}
