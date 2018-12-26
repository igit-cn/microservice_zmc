package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtyImage;

import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface SpecialtyImageService {
    void batchInsert(List<SpecialtyImage> specialtyImageList) throws Exception;

    void insert(SpecialtyImage s) throws Exception;
    /** 根据特产Id获取特产的图片(产品图片和logo图片)*/
    List<SpecialtyImage> getSpecialtyImageList(Long id) throws Exception;
}
