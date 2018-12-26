package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyVinbound;

/**
 * Created by xyy on 2018/12/10.
 *
 * @author xyy
 */
public interface HyVinboundService {
    /** 通过规格id获取虚拟库存*/
    HyVinbound findBySpecification(Long specificationId) throws Exception;
    /** 保存HyVinbound*/
    void saveHyVinbound(HyVinbound hyVinbound) throws Exception;
    /** 更新HyVinbound*/
    void updateHyVinbound(HyVinbound hyVinbound) throws Exception;
}
