package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Viplevel;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
public interface ViplevelService {
    /** 获取所有的会员等级*/
    List<Viplevel> findAll()throws Exception;
}
