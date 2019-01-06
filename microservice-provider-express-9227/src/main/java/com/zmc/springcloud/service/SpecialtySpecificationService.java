package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface SpecialtySpecificationService {

    /** 获取父规格List*/
    List<Map<String,Object>> getParentSpecificationList(Long specialtyid) throws Exception;

    /** 根据特产id获取该特产的所有规格*/
    List<SpecialtySpecification> getAllSpecification(Long id) throws Exception;

    /** 根据规格表的id获取某一规格*/
    SpecialtySpecification findById(Long specificationsId) throws Exception;

    /** 批量添加规格*/
    void batchInsert(List<SpecialtySpecification> specialtySpecificationList) throws Exception;

    /** 更新特产的相关属性*/
    void update(SpecialtySpecification spe) throws Exception;

    /** 基础库存是否足够*/
    boolean isBaseInboundEnough(List<Map<String, Object>> orderItems) throws Exception;

    /** 更新规格的基础库存和销量*/
    void updateBaseInboundAndHasSold(BusinessOrderItem businessOrderItem, Boolean isSale) throws Exception;
}
