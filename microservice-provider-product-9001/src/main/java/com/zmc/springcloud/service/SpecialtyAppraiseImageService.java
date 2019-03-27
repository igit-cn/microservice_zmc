package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtyAppraiseImage;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
public interface SpecialtyAppraiseImageService {
    /** 根据specialtyAppraisedId获取评论图片列表*/
    List<SpecialtyAppraiseImage> getAppraiseImage(Long specialtyAppraiseId) throws Exception;
}
