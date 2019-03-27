package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtyPrice;


/**
 * Created by xyy on 2018/12/10.
 *
 * @author xyy
 */
public interface SpecialtyPriceService {
    /** 通过规格id筛选isActive的特产价格*/
    SpecialtyPrice find(Long id, boolean isActive) throws Exception;
    /** 新建 SpecialtyPrice*/
    void save(SpecialtyPrice newprice) throws Exception;
    /** 价格等变化 将之前的SpecialtyPrice的isActive置为false*/
    void updateSpecialtyPrice(Long id) throws Exception;
}
