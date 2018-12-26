package com.zmc.springcloud.service;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface SpecialtyAppraiseService {
    /** 获取某一种产品的评论数*/
    Long count(Long specialtyId) throws Exception;
}
