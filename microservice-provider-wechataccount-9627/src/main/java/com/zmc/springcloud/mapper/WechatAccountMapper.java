package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.WechatAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Mapper
public interface WechatAccountMapper {
    @Select("SELECT FROM hy_wechat_account WHERE id = #{id}")
    WechatAccount findById(Long id);

    @Update("UPDATE hy_wechat_account SET totalbalance = #{totalbalance} WHERE id = #{id}")
    void updateTotalBalance(WechatAccount wechatAccount);

    @Update("UPDATE hy_wechat_account SET is_new = #{isNew} WHERE id = #{id}")
    void upIsNew(WechatAccount wechatAccount);

    @Update("UPDATE hy_wechat_account SET is_vip = #{isVip} WHERE id = #{id}")
    void updateVip(WechatAccount wechatAccount);

    /** 更新vip等级 可用积分 总积分 用户余额*/
    @Update("UPDATE hy_wechat_account SET is_vip = #{isVip}, point = #{point}, totalpoint = #{totalpoint}, totalbalance = #{totalbalance}")
    void updateVipPointTotalpointTotalbalance(WechatAccount wechatAccount);

    /** 根据openId获取WechatAccount的List*/
    @Select("SELECT * FROM hy_wechat_account WHERE wechat_openid = #{openId}")
    List<WechatAccount> findWechatAccountListByOpenId(String openId);

    @Insert("INSERT INTO hy_wechat_account(is_active, totalbalance, wechat_name, wechat_openid, is_vip, create_time, is_we_business, totalpoint, point, is_new) VALUES(#{isActive}, #{totalbalance}, #{wechatName}, #{wechatOpenid}, #{isVip}, NOW(), #{isWeBusiness}, #{totalpoint}, #{point}, #{isNew})")
    void insert(WechatAccount wechatAccount);
}
