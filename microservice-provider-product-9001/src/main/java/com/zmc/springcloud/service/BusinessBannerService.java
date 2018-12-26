package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessBanner;

import java.util.Date;

/**
 * Created by xyy on 2018/12/12.
 *
 * @author xyy
 */
public interface BusinessBannerService {
    /** 根据targetId和type修改banner的状态*/
    void updateBannerState(Long targetId, int type, Boolean state, Date startTime, Date endTime) throws Exception;
    /** 根据targetId和type修改banner*/
    BusinessBanner getBusinessBanner(Long id, int ordinal) throws Exception;
    /** 新建BusinessBanner*/
    void saveBusinessBanner(BusinessBanner banner) throws Exception;
}
