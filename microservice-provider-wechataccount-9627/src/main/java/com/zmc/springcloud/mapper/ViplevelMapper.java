package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Viplevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Mapper
public interface ViplevelMapper {
    /** 获取所有的会员等级*/
    @Select("SELECT * FROM hy_viplevel")
    List<Viplevel> findAll();
    /** 根据微信账户表id获取Viplevel*/
    @Select("SELECT * FROM hy_viplevel WHERE id = (SELECT viplevel_id FROM hy_vip WHERE wechat_account = #{wechatAccountId})")
    Viplevel getViplevelBywechataccountId(Long wechatAccountId);
}
