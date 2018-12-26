package com.zmc.springcloud.mapper;

import com.zmc.springcloud.util.WechatOfficialAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Mapper
public interface WechatOfficialAccountMapper {
    /** 注意没有参数*/
    @Select("SELECT * FROM hy_wechat_official_account WHERE is_valid = 1")
    List<WechatOfficialAccount> findList();
}
