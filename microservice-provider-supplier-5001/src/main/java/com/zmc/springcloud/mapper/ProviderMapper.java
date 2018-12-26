package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Provider;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
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


    @Insert("INSERT INTO hy_provider(account_type, address, bank_account, bank_code,bank_name, contactor_email, contactor_mobile, contactor_name, contactor_postcode,contactor_qq, contactor_wechat, contactor_number,end_time,introduction, operator, postcode, provider_name, provider_type, start_time, state,create_time, account_name, account_user, balance_type, balance_date) VALUES(#{accountType},#{address},#{bankAccount},#{bankCode},#{bankName},#{contactorEmail},#{contactorMobile},#{contactorName},#{contactorPostcode},#{contactorQq},#{contactorWechat},#{contractNumber},#{endTime},#{introduction},#{usernameInSession},#{postcode},#{providerName},#{providerType},#{startTime},#{state},NOW(),#{accountName},#{username},#{balanceType},#{balanceDate})")
    void insert(
            @Param(value = "accountType") Integer accountType,
            @Param(value = "address") String address,
            @Param(value = "bankAccount") String bankAccount,
            @Param(value = "bankCode") String bankCode,
            @Param(value = "bankName") String bankName,
            @Param(value = "contactorEmail") String contactorEmail,
            @Param(value = "contactorMobile") String contactorMobile,
            @Param(value = "contactorName") String contactorName,
            @Param(value = "contactorPostcode") String contactorPostcode,
            @Param(value = "contactorQq") String contactorQq,
            @Param(value = "contactorWechat") String contactorWechat,
            @Param(value = "contractNumber") String contractNumber,
            @Param(value = "endTime") Date endTime,
            @Param(value = "introduction") String introduction,
            @Param(value = "usernameInSession") String usernameInSession,
            @Param(value = "postcode") String postcode,
            @Param(value = "providerName") String providerName,
            @Param(value = "providerType") Integer providerType,
            @Param(value = "startTime") Date startTime,
            @Param(value = "state") Boolean state,
            @Param(value = "accountName") String accountName,
            @Param(value = "username") String username,
            @Param(value = "balanceType") Integer balanceType,
            @Param(value = "balanceDate") Integer balanceDate);


}
