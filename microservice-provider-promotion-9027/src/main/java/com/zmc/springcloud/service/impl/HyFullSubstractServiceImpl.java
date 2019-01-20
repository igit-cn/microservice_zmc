package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyFullSubstract;
import com.zmc.springcloud.mapper.HyFullSubstractMapper;
import com.zmc.springcloud.service.HyFullSubstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class HyFullSubstractServiceImpl implements HyFullSubstractService{
    @Autowired
    private HyFullSubstractMapper hyFullSubstractMapper;

    @Override
    public List<HyFullSubstract> getHyFullSubstractListByPromotionId(Long promotionId) throws Exception {
        return hyFullSubstractMapper.getHyFullSubstractListByPromotionId(promotionId);
    }
}
