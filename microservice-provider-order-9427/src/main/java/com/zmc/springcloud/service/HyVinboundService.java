package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.HyVinbound;
import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.List;

/**
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
public interface HyVinboundService {
    List<HyVinbound> getHyVinboundListBySpecification(SpecialtySpecification fuSpecification) throws Exception;
    /** 修改虚拟库存的数量*/
    void updateHyVinboundNum(Long id, Integer saleNumber, Integer vinboundNumber) throws Exception;
}
