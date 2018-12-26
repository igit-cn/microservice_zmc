package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.SpecialtyCategory;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by xyy on 2018/11/23.
 * @author xyy
 */
@Mapper
public interface LoginMapper {
    /**
     * 根据username获取HyAdmin
     */
    @Select("SELECT * FROM hy_admin WHERE username = #{userName}")
    HyAdmin findByUserName(String userName);
}
