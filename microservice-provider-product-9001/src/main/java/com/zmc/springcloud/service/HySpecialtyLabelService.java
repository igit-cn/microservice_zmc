package com.zmc.springcloud.service;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
public interface HySpecialtyLabelService {
    boolean isMarked(Long id, Long specialtyId, boolean isMarked) throws Exception;
}
