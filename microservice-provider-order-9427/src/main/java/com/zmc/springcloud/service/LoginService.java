package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyAdmin;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface LoginService {
    HyAdmin getHyAdmin(String username) throws Exception;
}
