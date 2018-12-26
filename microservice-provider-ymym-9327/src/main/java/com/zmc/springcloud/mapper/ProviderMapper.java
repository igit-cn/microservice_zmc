package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
@Mapper
public interface ProviderMapper {
    @Select("SELECT * FROM hy_provider WHERE id = #{providerId}")
    Provider findById(Long providerId);
}
