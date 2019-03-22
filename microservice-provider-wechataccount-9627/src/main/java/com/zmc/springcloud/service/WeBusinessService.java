package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.WeBusiness;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
public interface WeBusinessService {
    /** 根据id获取WeBusiness*/
    WeBusiness getWeBusinessById(Long id) throws Exception;

    /** 根据openId获取WeBusiness的list*/
    List<WeBusiness> getWeBusinessListByOpenId(String openId) throws Exception;


}
