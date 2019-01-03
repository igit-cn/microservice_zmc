package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyArea;

/**
 * Created by xyy on 2018/12/8.
 *
 * @author xyy
 */
public interface HyAreaService {
    HyArea getHyAreaById(Long areaId) throws Exception;
}
