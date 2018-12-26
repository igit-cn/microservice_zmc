package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.mapper.LoginMapper;
import com.zmc.springcloud.service.LoginService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public HyAdmin getHyAdmin(String username) throws Exception {
        return loginMapper.findByUserName(username);
    }
}
