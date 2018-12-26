package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/11/23.
 *
 * @author xyy
 */
@Mapper
public interface LoginMapper {
    /**
     * 根据username获取HyAdmin
     */
    @Select("SELECT * FROM hy_admin WHERE username = #{userName}")
    HyAdmin findByUserName(String userName);

    /**
     * 新增新的HyAdmin  注意 password,is_enabled,create_date
     */
    @Insert("INSERT INTO hy_admin(username, mobile,address, wechat, name, role, department, password,is_enabled,create_date) VALUES(#{username}, #{accountmobile}, #{accountaddress}, #{accountwechat}, #{accountname}, #{roleId}, #{department},#{password}, 1, NOW()) ")
    void insert(@Param(value = "username") String username, @Param(value = "accountmobile") String accountmobile, @Param(value = "accountaddress") String accountaddress, @Param(value = "accountwechat") String accountwechat, @Param(value = "accountname") String accountname, @Param(value = "roleId") Long roleId, @Param(value = "department") Long department, @Param(value = "password") String password);
}
