package com.zmc.springcloud.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.supplier.ProviderFeignClient;
import com.zmc.springcloud.service.*;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 特产
 * Created by xyy on 2018/11/23.
 */
@RestController
public class SpecialtyController {
    @Autowired
    private ProviderFeignClient providerFeignClient;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialtyCategoryService specialtyCategoryService;


    /**
     * 系统配置-分区管理-列表
     */
    @RequestMapping(value = "/category/page/view")
    public Json categoryList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Boolean isActive, String name) {
        Json j = new Json();
//        try {
//            HashMap<String, Object> p = specialtyCategoryService.getSpecialtyCategoryList(page, rows, isActive, name);
//            j.setMsg("操作成功");
//            j.setObj(p);
//            j.setSuccess(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            j.setSuccess(false);
//            j.setMsg(e.getMessage());
//        }
        return j;
    }

    /**
     * 系统配置-分区管理-新建-获取父分区
     */
    @RequestMapping(value = "/category/selectlist/view")
    public Json selectlist(Long id, Boolean isActive, String name) {
        Json j = new Json();
        try {
            List<HashMap<String, Object>> list = specialtyCategoryService.getSpecialtyCategorySelectlist(id, isActive, name);
            j.setMsg("操作成功");
            j.setObj(list);
            j.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 系统配置-分区管理-新建-保存
     */
    @RequestMapping(value = "/category/add")
    public Json categoryAdd(String name, Boolean isActive, String parentName, Long pid, String iconUrl, HttpSession session) {
        Json j = new Json();
        try {
            String username = (String) session.getAttribute(CommonAttributes.Principal);
            j = specialtyCategoryService.addSpecialtyCategory(name, isActive, parentName, pid, iconUrl, username);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**编辑部-产品管理-列表-特产分区下拉列表
     * 渠道销售-产品管理-列表-特产分区下拉列表
     * */
    @RequestMapping(value = "/category/treelist/view")
    public Json specialtyCategoryTreeList(){
        Json j = new Json();
        try{
            List<HashMap<String, Object>> list = specialtyCategoryService.getSpecialtyCategoryTreeList();
            j.setObj(list);
            j.setSuccess(true);
            j.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    //分区产品分割线==================================================================================================================

    // TODO 从模块划分的角度来说,这个接口应该放到供应商模块 但前台的URL写成了 而不是 /admin/business/provider
    /**编辑部-产品管理-列表-特产供应商下拉列表
     * 渠道销售-产品管理-列表-特产供应商下拉列表
     * */
    @RequestMapping(value = "/provider/list/view")
    public Json providerList(){
        Json j = new Json();
        try{
            List<Provider> list = providerFeignClient.getListProvider(true, null, null, null);
            j.setSuccess(true);
            j.setObj(list);
            j.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 编辑部-产品管理-列表
     *  渠道销售-产品管理-列表
     * */
    @RequestMapping(value = "/page/view")
    public Json productList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Long providerid, String name, Long categoryid, HttpSession session){
        Json j = new Json();
        try{
            String username = (String) session.getAttribute(CommonAttributes.Principal);
            HashMap<String , Object> obj = specialtyService.getProductList(page, rows, providerid, name, categoryid, username);
            j.setObj(obj);
            j.setSuccess(true);
            j.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    // 不使用包装类,直接用JSON接收  注意JSONArray和JSONObject
    /** 编辑部-产品管理-新建*/
    @RequestMapping(value = "/add")
    public Json add(@RequestBody JSONObject specialty, HttpSession session) {
        Json j = new Json();
        try {
            JSONObject jsonObject = specialty.getJSONObject("specialty");
            String usernameInSession = (String) session.getAttribute(CommonAttributes.Principal);
            specialtyService.addProduct(jsonObject, usernameInSession);
            j.setSuccess(true);
            j.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 渠道销售-产品管理-查看产品-详情*/
    @RequestMapping(value = "/detail/view")
    public Json detail(Long id){
        Json j = new Json();
        try{
            HashMap<String, Object> obj = specialtyService.getSpecialtyDetail(id);
            j.setObj(obj);
            j.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 渠道销售-产品管理-查看产品-详情-相关推荐列表*/
    @RequestMapping(value = "/recommendlist/view")
    public Json recommendList(Long id){
        Json j = new Json();
        try{
            List<Map<String, Object>> listMap = specialtyService.getSpecialtiesForRecommendSpecialty(id);
            j.setObj(listMap);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 渠道销售-产品管理-查看产品-提交产品编辑*/
    @RequestMapping(value = "/qudao/modify")
    public Json specialtyQudaoModify(@RequestBody JSONObject paylaod, HttpSession session){
        Json j = new Json();
        try {
            String usernameInSession = (String) session.getAttribute(CommonAttributes.Principal);
            specialtyService.modifyProductByQudaoXiaoShou(paylaod, usernameInSession);
            j.setSuccess(true);
            j.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }
}
