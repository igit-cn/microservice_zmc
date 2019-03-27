package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.ServicePromise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
@Mapper
public interface ServicePromiseMapper {
    @Select("SELECT * FROM hy_service_promise")
    List<ServicePromise> findAll();
}
