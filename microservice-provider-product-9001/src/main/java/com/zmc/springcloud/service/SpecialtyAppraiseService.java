package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtyAppraise;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface SpecialtyAppraiseService {
    /** 获取某一种产品的评论数*/
    Long count(Long specialtyId) throws Exception;
    /** 根据specialtyid获取isShow isValid的SpecialtyAppraise*/
    HashMap<String, Object> getSpecialtyAppraiseBySpecialtyId(Integer page, Integer rows, Long specialtyid) throws Exception;
}
