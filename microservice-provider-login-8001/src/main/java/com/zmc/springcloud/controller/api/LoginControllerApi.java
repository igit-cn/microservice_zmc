package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/10.
 *
 * @author xyy
 */
@RestController
public class LoginControllerApi {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/admin/{username}")
    public HyAdmin getHyAdminByUserName(@PathVariable("username") String username) {
        return loginService.getByUserName(username);
    }

    @RequestMapping(value = "/admin/add")
    public void addHyAdmin(HyAdmin hyAdmin) {
        loginService.insertHyAdmin(hyAdmin);
    }
}
