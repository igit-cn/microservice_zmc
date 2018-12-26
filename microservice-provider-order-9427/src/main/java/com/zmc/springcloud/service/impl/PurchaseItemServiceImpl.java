package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.PurchaseItem;
import com.zmc.springcloud.mapper.PurchaseItemMapper;
import com.zmc.springcloud.service.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {
    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    @Override
    public PurchaseItem getPurchaseItemById(Long purchaseItemId) throws Exception {
        return purchaseItemMapper.findById(purchaseItemId);
    }
}
