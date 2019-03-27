package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyLabel;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
public interface HyLabelService {
    List<HyLabel> getHyLableListBySpecialtyId(Long specialtyId) throws Exception;
}
