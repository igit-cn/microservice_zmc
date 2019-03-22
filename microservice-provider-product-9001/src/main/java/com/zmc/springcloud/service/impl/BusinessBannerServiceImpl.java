package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessBanner;
import com.zmc.springcloud.mapper.BusinessBannerMapper;
import com.zmc.springcloud.service.BusinessBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/12/12.
 *
 * @author xyy
 */
@Service
public class BusinessBannerServiceImpl implements BusinessBannerService{
    @Autowired
    private BusinessBannerMapper businessBannerMapper;

    @Override
    public void updateBannerState(Long targetId, int type, Boolean state, Date startTime, Date endTime) throws Exception{
        businessBannerMapper.updateBannerState(targetId, type, state, startTime, endTime);
    }

    @Override
    public BusinessBanner getBusinessBanner(Long targetId, int type)throws Exception {
        return businessBannerMapper.findBusinessBanner(targetId, type);
    }

    @Override
    public void saveBusinessBanner(BusinessBanner banner)throws Exception {
        businessBannerMapper.insert(banner);
    }

    @Override
    public List<BusinessBanner> getBusinessBannerList(Boolean state) {
        return businessBannerMapper.findBusinessBannerListByState(state);
    }
}
