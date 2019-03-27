package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.SpecialtyAppraise;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.mapper.SpecialtyAppraiseMapper;
import com.zmc.springcloud.service.SpecialtyAppraiseImageService;
import com.zmc.springcloud.service.SpecialtyAppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class SpecialtyAppraiseServiceImpl implements SpecialtyAppraiseService {
    @Autowired
    private SpecialtyAppraiseMapper specialtyAppraiseMapper;

    @Autowired
    private SpecialtyAppraiseImageService specialtyAppraiseImageService;

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Override
    public Long count(Long specialtyId) throws Exception {
        return specialtyAppraiseMapper.count(specialtyId);
    }

    @Override
    public HashMap<String, Object> getSpecialtyAppraiseBySpecialtyId(Integer page, Integer rows, Long specialtyid) throws Exception {
        PageHelper.startPage(page, rows);
        List<SpecialtyAppraise> list = specialtyAppraiseMapper.findSpecialtyAppraiseBySpecialtyId(specialtyid);
        PageInfo pageInfo = new PageInfo(list);
        HashMap<String, Object> hashMap = new HashMap<>();
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (SpecialtyAppraise tmp : list) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("id", tmp.getId());
            if (tmp.getIsAnonymous() == null || tmp.getIsAnonymous() == true) {
                hm.put("wechatName", "*****");
            } else {
                Long accountId = tmp.getAccountId();
                WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(accountId);
                hm.put("wechatName", wechatAccount.getWechatName());
            }
            hm.put("appraiseTime", tmp.getAppraiseTime());
            hm.put("appraiseContent", tmp.getAppraiseContent());
            hm.put("contentLevel", tmp.getContentLevel());
            hm.put("images", specialtyAppraiseImageService.getAppraiseImage(tmp.getId()));
            hm.put("isAnonymous", tmp.getIsAnonymous());
            result.add(hm);
        }
        hashMap.put("total", pageInfo.getTotal());
        hashMap.put("pageNumber", page);
        hashMap.put("pageSize", rows);
        hashMap.put("rows", result);
        return hashMap;
    }
}
