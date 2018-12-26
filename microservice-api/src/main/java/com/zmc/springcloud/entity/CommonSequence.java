package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Data
public class CommonSequence {
    public enum SequenceTypeEnum {
        /** 线路国内产品ID自增 */
        xianlugn,

        /** 线路出境产品ID自增 */
        xianlucj,

        /** 线路汽车产品ID自增 */
        xianluqc,

        /** 商贸订单ID自增 */
        businessOrderSuq,

        /** 票务部景区门票产品ID自增 */
        piaowubump,

        /**订单编号**/
        orderSn,

        /**导游自增id**/
        guideSn,

        /** 票务部酒店房间产品ID自增 */
        piaowubujdfj,

        /**特产编号**/
        specialtySn,

        /**采购单编号**/
        purchaseSn,

        /**门店认购门票产品id*/
        SubscribeTicket,

        /** 票务部酒加景产品ID自增 */
        piaowubujjj,

        /** 打款确认单编号*/
        supplierSettlement,

        /** 分公司分成确认单编号 **/
        profitShareConfirm,

        /** 分公司打款确认单编号*/
        branchSettlement,

        /** 商城销售电子券订单编号*/
        couponSaleOrderSn,

        /** 签证产品ID*/
        piaowuqianzheng,

        /** 门店充值订单编号*/
        mendianRecharge,

        /** 票务酒店产品pn*/
        piaowujiudianPn,

        /** 票务景区产品pn*/
        piaowujingquPn;
    }
    private Long id;
    private SequenceTypeEnum type;
    private Long value;
}
