package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.SpecialtyCategory;
import com.zmc.springcloud.feignclient.login.HyAdminFeignClient;
import com.zmc.springcloud.mapper.SpecialtyCategoryMapper;
import com.zmc.springcloud.service.SpecialtyCategoryService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xyy on 2018/11/23.
 *
 * @author xyy
 */
@Service
public class SpecialtyCategoryServiceImpl implements SpecialtyCategoryService {
    @Autowired
    private SpecialtyCategoryMapper specialtyCategoryMapper;

    @Autowired
    private HyAdminFeignClient hyAdminFeignClient;

    @Override
    public SpecialtyCategory getSpecialtyCategoryById(Long categoryId)throws Exception {
        return specialtyCategoryMapper.findSpecialtyCategory(categoryId);
    }

    @Override
    public HashMap<String, Object> getSpecialtyCategoryList(Integer page, Integer rows, Boolean isActive, String name) throws Exception {
        PageHelper.startPage(page, rows);
        List<SpecialtyCategory> list = specialtyCategoryMapper.findListSpecialtyCategory(null, isActive, name, null);
        PageInfo pageInfo = new PageInfo(list);
        HashMap<String, Object> obj = new HashMap<>();
        List<HashMap<String, Object>> res = specialtyCategoryAndParentList(list);
        obj.put("rows", res);
        obj.put("pageNumber", page);
        obj.put("pageSize", rows);
        obj.put("total", pageInfo.getTotal());
        obj.put("totalPages", pageInfo.getPages());
        return obj;
    }

    @Override
    public List<HashMap<String, Object>> getSpecialtyCategorySelectlist(Long id, Boolean isActive, String name) throws Exception {
        List<SpecialtyCategory> list =  specialtyCategoryMapper.findListSpecialtyCategory(id, isActive, name, null);
        List<HashMap<String, Object>> res = specialtyCategoryAndParentList(list);
        return res;
    }

    @Override
    public Json addSpecialtyCategory(String name, Boolean isActive, String parentName, Long pid, String iconUrl, String username) throws Exception {
        Json json = new Json();
        HyAdmin admin = hyAdminFeignClient.getHyAdminByUserName(username);

        // 父分区校验
        SpecialtyCategory parent = specialtyCategoryMapper.findSpecialtyCategory(pid);
        if (parent == null) {
            json.setSuccess(false);
            json.setMsg("父分区不存在");
            return json;
        }

        // 分区重名校验
        List<SpecialtyCategory> result = specialtyCategoryMapper.findListSpecialtyCategory(null, null, name, null);
        if (result.size() > 0) {
            json.setSuccess(false);
            json.setMsg("设置失败,分区已存在");
            return json;
        }

        specialtyCategoryMapper.insert(name, isActive, pid, iconUrl, admin.getName());
        json.setSuccess(true);
        json.setMsg("添加成功");
        return json;
    }

    @Override
    public List<HashMap<String, Object>> getSpecialtyCategoryTreeList() throws Exception {
        List<HashMap<String, Object>> obj = new ArrayList<>();
        List<SpecialtyCategory> list = specialtyCategoryMapper.findListSpecialtyCategoryParent();
        for (SpecialtyCategory parent : list) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("value", parent.getId());
            hm.put("label", parent.getName());
            hm.put("children", fieldFilter(parent));
            obj.add(hm);
        }
        return obj;
    }



    private List<HashMap<String, Object>> fieldFilter(SpecialtyCategory parent)throws Exception {
        List<HashMap<String, Object>> res = new ArrayList<>();
        List<SpecialtyCategory> childrenList = specialtyCategoryMapper.findListSpecialtyCategory(null, true, null, parent.getId());
        if (!childrenList.isEmpty()) {
            for (SpecialtyCategory child : childrenList) {
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("value", child.getId());
                hm.put("label", child.getName());
                hm.put("children", fieldFilter(child));
                res.add(hm);
            }
        }
        return res;
    }

    /** 分区列表 包含直接父分区*/
    public List<HashMap<String, Object>> specialtyCategoryAndParentList(List<SpecialtyCategory> list){
        List<HashMap<String, Object>> res = new LinkedList<>();
        for (SpecialtyCategory category : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("createTime", category.getCreateTime());
            map.put("deadTime", category.getDeadTime());
            map.put("iconUrl", category.getIconUrl());
            map.put("id", category.getId());
            map.put("isActive", category.getIsActive());
            map.put("ishow", category.getIshow());
            map.put("name", category.getName());
            map.put("operator", category.getOperator());
            map.put("orders", category.getOrders());
            // TODO 能这样写的基础是,前台只需要展示当前分区和直接父分区
            if(category.getPid() == null){
                map.put("parent", null);
            }else{
                SpecialtyCategory specialtyCategory = specialtyCategoryMapper.findSpecialtyCategory(category.getPid());
                map.put("parent", specialtyCategory);
            }
            res.add(map);
        }
        return res;
    }
}
