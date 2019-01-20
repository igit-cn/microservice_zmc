package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.PurchaseItem;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface PurchaseItemService {
    PurchaseItem getPurchaseItemById(Long purchaseItemId) throws Exception;
    /** 根据 pecificationId,  isValid,  state 获取PurchaseItem的List*/
    List<PurchaseItem> getPurchaseItemList(Long specificationId, Boolean isValid, Boolean state);
}
