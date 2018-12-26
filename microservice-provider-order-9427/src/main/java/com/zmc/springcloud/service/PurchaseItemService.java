package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.PurchaseItem;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface PurchaseItemService {
    PurchaseItem getPurchaseItemById(Long purchaseItemId) throws Exception;
}
