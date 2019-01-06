package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.mapper.PointRecordMapper;
import com.zmc.springcloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Service
public class PointRecordServiceImpl implements PointRecordService{
    @Autowired
    private PointRecordMapper pointRecordMapper;

    @Autowired
    private WechatAccountService wechatAccountService;

    @Autowired
    private CouponBalanceUseService couponBalanceUseService;

    @Autowired
    private ViplevelService viplevelService;

    @Autowired
    private VipService vipService;

    @Override
    public void changeUserPoint(Long orderWechatId, Integer changevalue, String reason) throws Exception {
        WechatAccount wechatAccount = wechatAccountService.findById(orderWechatId);
        if(wechatAccount == null){
            throw new Exception("微信用户无效");
        }
        // 添加积分记录
        PointRecord pointRecord = new PointRecord();
        pointRecord.setWechatAccount(orderWechatId);
        pointRecord.setChangevalue(changevalue);
        pointRecord.setReason(reason);
        pointRecord.setCreateTime(new Date());
        if("兑换余额电子券".equals(reason)){
            if(changevalue % 10 != 0){
                throw new Exception("兑换积分必须是10的倍数");
            }
            // 添加兑换余额记录
            pointRecord.setBalance(BigDecimal.valueOf(Math.abs(changevalue)/10));
            // 修改用户余额
            if(wechatAccount.getTotalbalance() == null){
                wechatAccount.setTotalbalance(BigDecimal.ZERO);
            }
            wechatAccount.setTotalbalance(wechatAccount.getTotalbalance().add(pointRecord.getBalance()));

            // 添加余额兑换记录
            CouponBalanceUse couponBalanceUse = new CouponBalanceUse();
            couponBalanceUse.setPhone(wechatAccount.getPhone());
            //5积分兑换
            couponBalanceUse.setType(5);
            couponBalanceUse.setState(1);
            couponBalanceUse.setUseAmount(pointRecord.getBalance().floatValue());
            couponBalanceUse.setUseTime(new Date());
            couponBalanceUse.setWechatId(wechatAccount.getId());
            couponBalanceUseService.save(couponBalanceUse);
        }else{
            // 不是兑换余额类型的记录，则要修改用户总积分
            wechatAccount.setTotalpoint(wechatAccount.getTotalpoint()+pointRecord.getChangevalue());
            // 积分变化记录余额为0
            pointRecord.setBalance(BigDecimal.ZERO);
            // 根据用户总积分，更新用户会员等级
            List<Viplevel> viplevels = viplevelService.findAll();
            for (Viplevel viplevel : viplevels) {
                Integer totalPoint = wechatAccount.getTotalpoint();
                if(totalPoint<=viplevel.getEndvalue()) {
                    if(totalPoint<=viplevel.getStartvalue() && wechatAccount.getIsVip().equals(false)) {
                        //如果低于最低值而且用户本身不是vip，则说明不能成为会员
                        break;
                    }
                    // 如果总积分在一定范围内，设置会员等级
                    if(wechatAccount.getIsVip().equals(false)) {
                        wechatAccount.setIsVip(true);
                    }
                    Vip vip = vipService.getVipByWechatAccount(wechatAccount.getId());
                    if(vip == null){
                        //如果没有会员,则新建会员记录
                        Vip newVip = new Vip();
                        newVip.setViplevelId(viplevel.getId());
                        newVip.setWechatAccount(wechatAccount.getId());
                        newVip.setCreateTime(new Date());
                        vipService.save(newVip);
                    }else{
                        vip.setViplevelId(viplevel.getId());
                        vipService.updateVipLevel(vip);
                    }
                    break;
                }
            }
        }
        // 修改用户可用积分
        if(wechatAccount.getPoint() == null){
            wechatAccount.setPoint(0);
        }
        wechatAccount.setPoint(wechatAccount.getPoint() + pointRecord.getChangevalue());
        // 保存记录修改
        wechatAccountService.updateVipPointTotalpointTotalbalance(wechatAccount);

        pointRecordMapper.save(pointRecord);
    }
}
