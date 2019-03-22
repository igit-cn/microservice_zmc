package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.WeBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@Mapper
public interface WeBusinessMapper {
    /** 根据id获取WeBusiness*/
    @Select("SELECT * FROM hy_we_business WHERE id = #{id}")
    WeBusiness selectWeBusinessById(Long id);

    /** 根据openId获取WeBusiness的list*/
    @Select("SELECT * FROM hy_we_business WEHRE wechat_open_id = #{openId}")
    List<WeBusiness> selectWeBusinessListByOpenId(String openId);


}
