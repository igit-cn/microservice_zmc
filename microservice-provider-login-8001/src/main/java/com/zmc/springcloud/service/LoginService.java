package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.HyRole;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by xyy on 2018/11/20.
 */
public interface LoginService {
    /**
     * 根据username获取HyAdmin
     * */
    HyAdmin getByUserName(String username);

    /**
     *
     * */
    void insertHyAdmin(HyAdmin hyAdmin);

    /**
     * 登录提交
     */
    Boolean loginCheck(HyAdmin hyAdmin) throws Exception;
    /**
     * 获取左侧导航栏
     * */
    HashMap<String, Object> getMenu(String username) throws Exception;

    /** 返回当前登录用户可以分配的子角色*/
    Set<HyRole> getSubRoles(String username) throws Exception;


}
