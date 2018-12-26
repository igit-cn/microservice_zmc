package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.HyAuthority;
import com.zmc.springcloud.entity.HyRole;
import com.zmc.springcloud.entity.HyRoleAuthority;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.util.List;

/**
 * Created by xyy on 2018/11/20.
 */
@Mapper
public interface LoginMapper {
    /**
     * 根据username获取HyAdmin
     */
    @Select("SELECT * FROM hy_admin WHERE username = #{userName}")
    HyAdmin findByUserName(String userName);

    /**
     * 根据id获取HyRole
     */
    @Select("SELECT * FROM hy_role WHERE id = #{id}")
    HyRole findRoleById(Long id);

    /**
     * 获取所有可展示的左侧导航栏
     */
    @Results({@Result(property = "range", column = "range", typeHandler = EnumOrdinalTypeHandler.class),
              @Result(property = "operation", column = "operation", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_authority WHERE is_display = 1 ORDER BY id")
    List<HyAuthority> findAllAuthority();

    /**
     * 根据id查询权限
     */
    @Results({@Result(property = "range", column = "range", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "operation", column = "operation", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_authority WHERE id = #{hyAuthorityId}")
    HyAuthority findHyAuthorityById(Long hyAuthorityId);

    /**
     * 根据父权限查找子权限
     */
    @Results({@Result(property = "range", column = "range", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "operation", column = "operation", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_authority WHERE pID = #{pID}")
    List<HyAuthority> findHyAuthorityChildrenByPid(Long pID);


    @Results({@Result(property = "rangeCheckedNumber", column = "range_checked_number", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "operationCheckedNumber", column = "operation_checked_number", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_role_authority WHERE roles = #{roleId} ORDER BY authoritys")
    List<HyRoleAuthority> findHyRoleAuthorityByRoleId(Long roleId);


    @Results({@Result(property = "rangeCheckedNumber", column = "range_checked_number", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "operationCheckedNumber", column = "operation_checked_number", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_role_authority WHERE roles = #{roleId} AND authoritys = #{authorityId} ORDER BY id")
    List<HyRoleAuthority> findHyRoleAuthorityByRoleIdAndAuthorityId(@Param(value = "roleId") Long roleId, @Param(value = "authorityId") Long authorityId);

    /**
     * 返回当前登录用户可以分配的子角色
     */
    @Select("SELECT * FROM hy_role WHERE status = 1 AND id IN (SELECT subroles FROM hy_roles_subroles WHERE roles = (SELECT role FROM hy_admin WHERE username = #{username}));")
    List<HyRole> getSubRoles(String username);
}
