package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Viplevel;
import com.zmc.springcloud.service.ViplevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@RestController
public class VipLevelControllerApi {
    @Autowired
    private ViplevelService viplevelService;

    @RequestMapping("/viplevel/wechataccount/{wechataccountid}")
    public Viplevel getViplevelBywechataccountId(@PathVariable Long wechataccountid){
        return  viplevelService.getViplevelBywechataccountId(wechataccountid);
    }
}
