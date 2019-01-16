package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Receiver;
import com.zmc.springcloud.mapper.ReceiverMapper;
import com.zmc.springcloud.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/1/16.
 *
 * @author xyy
 */
@Service
public class ReceiverServiceImpl implements ReceiverService{
    @Autowired
    private ReceiverMapper receiverMapper;
    @Override
    public List<Receiver> getReceriverListByWechatId(Long wechatId) throws Exception {
        return receiverMapper.getReceriverListByWechatId(wechatId);
    }

    @Override
    public void updateIsDefaultRecieverAddress(Receiver r) {
        receiverMapper.updateIsDefaultRecieverAddress(r);
    }

    @Override
    public void save(Receiver receiver) {
        receiverMapper.insert(receiver);
    }

    @Override
    public void updateExceptWechatId(Receiver receiver) {
        receiverMapper.updateExceptWechatId(receiver);
    }

    @Override
    public void delete(Long id) {
        receiverMapper.delete(id);
    }
}
