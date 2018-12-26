package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM hy_admin WHERE username = #{username}")
    HyAdmin findByUserName(String username);
}
