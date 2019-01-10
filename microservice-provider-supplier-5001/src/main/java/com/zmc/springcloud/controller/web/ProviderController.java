package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.Provider;
import com.zmc.springcloud.service.ProviderService;
import com.zmc.springcloud.utils.CheckedOperation;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2018/11/29.
 *
 * @author xyy
 */
@RestController
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    /**
     * 采购部 特产供应商 列表
     */
    @RequestMapping(value = "/admin/business/provider/page/view")
    public Json providerPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Boolean state, Long providerType, String providerName, String contactorName, HttpServletRequest request) {
        Json j = new Json();
        try {
            String username = (String) request.getSession().getAttribute(CommonAttributes.Principal);
            /**获取用户权限范*/
            CheckedOperation co = (CheckedOperation) request.getAttribute("co");
            HashMap<String, Object> p = providerService.getProviderPage(page, rows, state, providerType, providerName, contactorName, co, username);
            j.setMsg("操作成功");
            j.setObj(p);
            j.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 采购部 特产供应商 新建
     */
    @RequestMapping("/admin/business/provider/add")
    public Json add(
            String providerName,
            Integer providerType,
            String contractNumber,
            String address,
            String postcode,
            Integer balanceType,
            String introduction,
            String contactorName,
            String contactorMobile,
            String contactorPostcode,
            String contactorQq,
            String contactorWechat,
            String contactorEmail,
            String bankName,
            String accountName,
            String bankAccount,
            String bankCode,
            Integer accountType,
            String username,
            String accountname,
            String accountmobile,
            String accountwechat,
            String accountaddress,
            Long roleid,
            Date startTime,
            Date endTime,
            Integer balanceDate,
            Boolean state,
            HttpSession session
    ) {
        String usernameInSession = (String) session.getAttribute(CommonAttributes.Principal);
        Json j = new Json();
        try {
            providerService.addProvider(
                    usernameInSession,
                    providerName,
                    providerType,
                    contractNumber,
                    address,
                    postcode,
                    balanceType,
                    introduction,
                    contactorName,
                    contactorMobile,
                    contactorPostcode,
                    contactorQq,
                    contactorWechat,
                    contactorEmail,
                    bankName,
                    accountName,
                    bankAccount,
                    bankCode,
                    accountType,
                    username,
                    accountname,
                    accountmobile,
                    accountwechat,
                    accountaddress,
                    roleid,
                    startTime,
                    endTime,
                    balanceDate,
                    state
            );
            j.setMsg("操作成功");
            j.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

}
