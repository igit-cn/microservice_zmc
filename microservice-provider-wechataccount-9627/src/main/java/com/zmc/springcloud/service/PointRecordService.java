package com.zmc.springcloud.service;

/**
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
public interface PointRecordService {
    /** 修改用户积分*/
    void changeUserPoint(Long orderWechatId, Integer changevalue, String reason) throws Exception;
}
