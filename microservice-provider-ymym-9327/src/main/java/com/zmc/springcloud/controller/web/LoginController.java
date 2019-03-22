package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.feignclient.wechataccount.WeBusinessFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@RestController
public class LoginController {
    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private WeBusinessFeignClient weBusinessFeignClient;

    @PostMapping("/login/submit")
    @ResponseBody
    public Json submit(
            @RequestParam(value = "wechat_openid") String wechatOpenid,
            @RequestParam(value = "wechat_name") String wechatName,
            @RequestParam(value = "webusiness_id") Long webusinessId,
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Json j = new Json();
        if (wechatOpenid == null || webusinessId == null) {
            j.setSuccess(false);
            j.setMsg("登录失败");
            j.setObj("微商id或微信openid不能为空");
            return j;
        }

        List<WechatAccount> wechatAccounts = wechatAccountFeignClient.getWechatAccountListByOpenId(wechatOpenid);
        if (wechatAccounts == null || wechatAccounts.isEmpty()) {
            WechatAccount wechatAccount = new WechatAccount();
            wechatAccount.setWechatName(wechatName);
            wechatAccount.setWechatOpenid(wechatOpenid);
            wechatAccount.setIsActive(true);
            wechatAccount.setTotalbalance(BigDecimal.ZERO);
            wechatAccount.setIsVip(false);
            wechatAccount.setTotalpoint(0);
            wechatAccount.setPoint(0);
            wechatAccount.setIsNew(false);
            List<WeBusiness> weBusiness = weBusinessFeignClient.getWeBusinessListByOpenId(wechatOpenid);
            wechatAccount.setIsWeBusiness(weBusiness != null && !weBusiness.isEmpty());
            wechatAccountFeignClient.createWechatAccount(wechatAccount);
            wechatAccounts.add(wechatAccount);
        }

        WechatAccount wechatAccount = wechatAccounts.get(0);
        if (wechatAccount != null) {
            session.setAttribute("webusiness_id", webusinessId);
            session.setAttribute("wechat_id", wechatAccount.getId());
            WebUtils.addCookie(request, response, "wechat_id", wechatAccount.getId().toString());
            WebUtils.addCookie(request, response, "webusiness_id", webusinessId.toString());
            j.setSuccess(true);
            j.setMsg("登录成功");
            j.setObj(wechatAccount);
            return j;
        }
        j.setSuccess(false);
        j.setMsg("登录失败");
        return j;
    }
}
