package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Vip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Mapper
public interface VipMapper {
    /** 根据wechatAccount获取Vip*/
    @Select("SELECT * FROM hy_vip WHERE wechat_account = #{wechatAccountId}")
    Vip getVipByWechatAccount(Long wechatAccountId);

    @Insert("INSERT INTO hy_vip(wechat_account, viplevel_id, create_time) VALUES(#{wechatAccount}, #{viplevelId}, #{createTime})")
    void insert(Vip newVip);

    /** 更新会员等级*/
    @Update("UPDATE hy_vip SET viplevel_id = #{viplevelId} WHERE id = #{id}")
    void updateVipLevel(Vip vip);
}
