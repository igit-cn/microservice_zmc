package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.PurchaseItem;
import com.zmc.springcloud.service.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@RestController
public class PurchaseItemControllerApi {
    @Autowired
    private PurchaseItemService purchaseItemService;

    @RequestMapping("/order/purchaseitem/list")
    public List<PurchaseItem> getPurchaseItemList(@RequestParam("specificationId") Long specificationId, @RequestParam("isValid") Boolean isValid, @RequestParam("state") Boolean state){
        return purchaseItemService.getPurchaseItemList(specificationId, isValid, state);
    }
}
