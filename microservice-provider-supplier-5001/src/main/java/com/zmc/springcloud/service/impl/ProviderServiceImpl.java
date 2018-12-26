package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.Provider;
import com.zmc.springcloud.mapper.LoginMapper;
import com.zmc.springcloud.mapper.ProviderMapper;
import com.zmc.springcloud.service.ProviderService;
import com.zmc.springcloud.utils.CheckedOperation;
import com.zmc.springcloud.utils.CommonAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xyy on 2018/11/29.
 *
 * @author xyy
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public HashMap<String, Object> getProviderPage(Integer page, Integer rows, Boolean state, Long providerType, String providerName,
                                                   String contactorName, CheckedOperation co, String username) throws Exception {
        PageHelper.startPage(page, rows);
        List<Provider> providers = providerMapper.findListProvider(state, providerType, providerName, contactorName);
        PageInfo pageInfo = new PageInfo(providers);

        List<Map<String, Object>> list = new ArrayList<>();
        for (Provider p : providers) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", p.getId());
            map.put("providerType", p.getProviderType());
            map.put("providerName", p.getProviderName());
            map.put("address", p.getAddress());
            map.put("postcode", p.getPostcode());
            map.put("introduction", p.getIntroduction());
            map.put("isContracted", p.getIsContracted());
            map.put("contractNumber", p.getContractNumber());
            map.put("startTime", p.getStartTime());
            map.put("endTime", p.getEndTime());
            map.put("state", p.getState());
            map.put("contactorName", p.getContactorName());
            map.put("contactorMobile", p.getContactorMobile());
            map.put("contactorEmail", p.getContactorEmail());
            map.put("contactorWechat", p.getContactorWechat());
            map.put("contactorQq", p.getContactorQq());
            map.put("contactorPostcode", p.getContactorPostcode());
            map.put("bankName", p.getBankName());
            map.put("accountName", p.getAccountName());
            map.put("bankAccount", p.getBankAccount());
            map.put("accountType", p.getAccountType());
            map.put("remark", p.getRemark());
            map.put("bankCode", p.getBankCode());
            map.put("createTime", p.getCreateTime());
            map.put("modifyTime", p.getModifyTime());
            map.put("cancelTime", p.getCancelTime());
            map.put("account", p.getAccountUser());
            String operator = p.getOperator();
            HyAdmin hyAdmin = loginMapper.findByUserName(operator);
            map.put("operatorName", hyAdmin.getName());

            /** 当前用户对本条数据的操作权限 */
            if (operator.equals(username)) {
                if (co == CheckedOperation.view) {
                    map.put("privilege", "view");
                } else {
                    map.put("privilege", "edit");
                }
            } else {
                if (co == CheckedOperation.edit) {
                    map.put("privilege", "edit");
                } else {
                    map.put("privilege", "view");
                }
            }
            list.add(map);
        }
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("rows", list);
        obj.put("pageNumber", page);
        obj.put("pageSize", rows);
        obj.put("total", pageInfo.getTotal());
        obj.put("totalPages", pageInfo.getPages());
        return obj;
    }

    @Override
    public void addProvider(String usernameInSession, String providerName, Integer providerType, String contractNumber, String address, String postcode, Integer balanceType,
                            String introduction, String contactorName, String contactorMobile, String contactorPostcode, String contactorQq,
                            String contactorWechat, String contactorEmail, String bankName, String accountName, String bankAccount, String bankCode,
                            Integer accountType, String username, String accountname, String accountmobile, String accountwechat,
                            String accountaddress, Long roleid, Date startTime, Date endTime, Integer balanceDate, Boolean state)
            throws Exception {

        //1.在hy_admin表中增加数据
        // 用户名重复校验
        if (null != loginMapper.findByUserName(username)) {
            throw new Exception("用户名已存在");
        }
        HyAdmin admin = loginMapper.findByUserName(usernameInSession);
        loginMapper.insert(username, accountmobile, accountaddress, accountwechat, accountname, roleid, admin.getDepartment(), CommonAttributes.DEFAULT_PASSWD);
        //2.在hy_provider表中增加数据
        providerMapper.insert(
                accountType,
                address,
                bankAccount,
                bankCode,
                bankName,
                contactorEmail,
                contactorMobile,
                contactorName,
                contactorPostcode,
                contactorQq,
                contactorWechat,
                contractNumber,
                endTime,
                introduction,
                usernameInSession,
                postcode,
                providerName,
                providerType,
                startTime,
                state,
                accountName,
                username,
                balanceType,
                balanceDate
        );
    }

}
