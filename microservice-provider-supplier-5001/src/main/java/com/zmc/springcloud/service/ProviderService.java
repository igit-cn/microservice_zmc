package com.zmc.springcloud.service;

import com.zmc.springcloud.utils.CheckedOperation;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by xyy on 2018/11/29.
 *
 * @author xyy
 */
public interface ProviderService {
    /** 采购部 特产供应商 列表*/
    HashMap<String,Object> getProviderPage(Integer page, Integer rows, Boolean state, Long providerType, String providerName, String contactorName, CheckedOperation co, String username) throws Exception;
    /** 采购部 特产供应商 新建*/
    void addProvider(String usernameInSession, String providerName, Integer providerType, String contractNumber, String address, String postcode, Integer balanceType, String introduction, String contactorName, String contactorMobile, String contactorPostcode, String contactorQq, String contactorWechat, String contactorEmail, String bankName, String accountName, String bankAccount, String bankCode, Integer accountType, String username, String accountname, String accountmobile, String accountwechat, String accountaddress, Long roleid, Date startTime, Date endTime, Integer balanceDate, Boolean state) throws Exception;
}
