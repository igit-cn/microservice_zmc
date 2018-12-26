package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Provider;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/11/29.
 *
 * @author xyy
 */
@Mapper
public interface ProviderMapper {


    /**
     * 根据筛选条件获取特产供应商列表
     */
    @SelectProvider(type = ProviderDaoProvicer.class, method = "findListProvider")
    List<Provider> findListProvider(Boolean state, Long providerType, String providerName, String contactorName);


    class ProviderDaoProvicer {
        public String findListProvider(Boolean state, Long providerType, String providerName, String contactorName) {
            String sql = "SELECT * FROM hy_provider WHERE 1=1";
            if (null != state) {
                sql += " AND state = " + state;
            }
            if (null != providerType) {
                sql += " AND provider_type = " + providerType;
            }
            if (StringUtils.isNotEmpty(providerName)) {
                sql += " AND provider_name LIKE '%" + providerName + "%'";
            }
            if (StringUtils.isNotEmpty(contactorName)) {
                sql += " AND contactor_name LIKE '%" + contactorName + "%'";
            }
            sql += " ORDER BY id DESC";
            return sql;
        }
    }

    @Select("SELECT * FROM hy_provider WHERE id = #{providerId}")
    Provider findById(Long providerId);
}
