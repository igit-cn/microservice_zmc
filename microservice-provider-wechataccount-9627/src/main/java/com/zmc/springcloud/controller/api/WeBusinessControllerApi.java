package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.service.WeBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@RestController
public class WeBusinessControllerApi {
    @Autowired
    private WeBusinessService weBusinessService;

    /** 根据id获取WeBusiness*/
    @GetMapping("/wechataccount/webusiness/{id}")
    public WeBusiness getWeBusinessById(Long id) throws Exception{
        return weBusinessService.getWeBusinessById(id);
    }

    /** 根据openId获取WeBusiness的List*/
    @GetMapping("/wechataccount/webusiness/{openId}")
    public List<WeBusiness> getWeBusinessListByOpenId(@PathVariable("openId")String openId) throws Exception{
        return weBusinessService.getWeBusinessListByOpenId(openId);
    }
}
