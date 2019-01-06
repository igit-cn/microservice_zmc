package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.PointRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Mapper
public interface PointRecordMapper {
    @Insert("INSERT INTO hy_pointrecord(wechat_account, changevalue, reason, balance, create_time) VALUES(#{wechatAccount}, #{reason}, #{changevalue}, #{balance},  #{createTime})")
    void save(PointRecord pointRecord) throws Exception;
}
