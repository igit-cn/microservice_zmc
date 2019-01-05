package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyVinbound;
import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.List;

/**
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
public interface HyVinboundService {
    /**
     * 通过规格id获取虚拟库存
     */
    HyVinbound findBySpecification(Long specificationId) throws Exception;

    /**
     * 保存HyVinbound
     */
    void saveHyVinbound(HyVinbound hyVinbound) throws Exception;

    /**
     * 更新HyVinbound
     */
    void updateHyVinbound(HyVinbound hyVinbound) throws Exception;

    List<HyVinbound> getHyVinboundListBySpecificationId(Long id) throws Exception;

    /**
     * 修改虚拟库存的数量
     */
    void updateHyVinboundNum(Long id, Integer saleNumber, Integer vinboundNumber) throws Exception;
}
