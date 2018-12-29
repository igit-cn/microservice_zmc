package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.HyRole;
import com.zmc.springcloud.service.LoginService;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by xyy on 2018/11/19.
 * @author xyy
 */
@RestController(value = "/common")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录提交
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Json submit(HyAdmin hyAdmin, HttpServletRequest request, HttpServletResponse response) {
        Json j = new Json();
        try {
            if (loginService.loginCheck(hyAdmin)) {
                HttpSession session = request.getSession();
                session.setAttribute(CommonAttributes.Principal, hyAdmin.getUsername());
                Cookie cookie = new Cookie("admin", hyAdmin.getUsername());
                cookie.setPath("/api");
                response.addCookie(cookie);
                j.setSuccess(true);
                j.setMsg("登录成功，转到主界面");
                return j;
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        j.setSuccess(false);
        j.setMsg("用户名或密码错误");
        return j;
    }

    /**
     * 获取左侧导航栏
     * */
    @RequestMapping(value = "/project/menu", method = RequestMethod.GET)
    public Json menu(HttpServletRequest request) {
        Json j = new Json();
        try {
            String username = (String) request.getSession().getAttribute(CommonAttributes.Principal);
            HashMap<String, Object> obj = loginService.getMenu(username);
            if (obj != null) {
                j.setObj(obj);
                j.setSuccess(true);
                j.setMsg("查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg(e.getMessage());
            j.setSuccess(false);

        }
        return j;
    }

    /** 返回当前登录用户可以分配的子角色*/
    @RequestMapping(value="/privilege/getSubroles", method = RequestMethod.GET)
    public Json getSubRoles(HttpSession session) {
        Json j = new Json();
        try {
            String username = (String) session.getAttribute(CommonAttributes.Principal);
            Set<HyRole> subRoles = loginService.getSubRoles(username);
            j.setObj(subRoles);
            j.setSuccess(true);
            j.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }
}
