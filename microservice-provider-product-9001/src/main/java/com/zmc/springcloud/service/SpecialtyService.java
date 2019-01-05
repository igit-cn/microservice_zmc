package com.zmc.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
public interface SpecialtyService {
    Specialty getSpecialtyById(Long id);
    /**
     * 编辑部-产品管理-列表
     */
    HashMap<String, Object> getProductList(Integer page, Integer rows, Long providerid, String name, Long categoryid, String userNameInSession) throws Exception;

    /**
     * 编辑部-产品管理-新建
     */
    void addProduct(JSONObject jsonObject, String usernameInSession) throws Exception;

    /** 渠道销售-产品管理-查看产品-详情-相关推荐列表*/
    List<Map<String,Object>> getSpecialtiesForRecommendSpecialty(Long id) throws Exception;

    /** 渠道销售-产品管理-查看产品-详情*/
    HashMap<String,Object> getSpecialtyDetail(Long id) throws Exception;

    /** 渠道销售-产品管理-查看产品-提交产品编辑*/
    void modifyProductByQudaoXiaoShou(JSONObject paylaod, String usernameInSession) throws Exception;
}
