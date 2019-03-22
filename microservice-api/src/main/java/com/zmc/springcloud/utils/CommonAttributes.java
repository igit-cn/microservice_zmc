package com.zmc.springcloud.utils;

/**
 * 公共参数
 */
public final class CommonAttributes {
    /**
     * 系统管理员角色的字符串
     */
    public static final String ROOT_ROLE = "admin";

    /**
     * session中的登录用户信息
     */
    public static final String Principal = "principal";
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String BaiduMapApiAccount = "baidu_map_api_account";
    /** hy_admin表的默认密码12345的MD5签名值*/
    public static final String DEFAULT_PASSWD= "827ccb0eea8a706c4c34a16891f84e7b";

    /**
     * 日期格式配比
     */
    public static final String[] DATE_PATTERNS = new String[]{"yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"};

    /**
     * shopxx.xml文件路径
     */
    public static final String GRAIN_XML_PATH = "/grain.xml";

    /**
     * shopxx.properties文件路径
     */
    public static final String GRAIN_PROPERTIES_PATH = "/grain.properties";

    public static final String SessionInfo = "sessionInfo";
    //1 直属库实时数据（含app）釆虫图片路径
    public static final String RealDataPicPath = "realdata";

    //2 农户储粮釆虫图片 路径
    public static final String FarmerStoredGrainInsectsPicPath = "farmer";

    //3 加工厂釆虫图片 path
    public static final String Factory_picPath = "factory";

    //4 野外釆虫图片 path
    public static final String Field_picPath = "field";

    //5 储备库釆虫图片 path
    public static final String depot_picPath = "depot";

    //6 分类特征图片 path
    public static final String catalog_picPath = "catalog";

    //7 特征图片 path
    public static final String digital_picPath = "digital";

    //8 防治工艺附件 path
    public static final String preventProcessDetach_Path = "preventProcessDetach";

    //9 诊断申请图片 path
    public static final String remote_diagnosis_path = "remotediagnosis";

    //其他
    public static final String else_path = "upload";

    /**
     * 不可实例化
     */
    private CommonAttributes() {
    }



    public static final String IMAGE_UPLOAD_PATH = "/upload/image/${.now?string('yyyyMM')}/";
    public static final String FLASH_UPLOAD_PATH = "/upload/flash/${.now?string('yyyyMM')}/";
    public static final String MEDIA_UPLOAD_PATH = "/upload/media/${.now?string('yyyyMM')}/";
    public static final String FILE_UPLOAD_PATH = "/upload/file/${.now?string('yyyyMM')}/";

    public static class WeBusinessType {
        public static final int Interior = 0;
        public static final int Exterior = 1;

    }


    /************************商贸订单状态***********************************/
    /**
     * 待付款
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_PAY = 0;
    /**
     * 待审核
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_REVIEW = 1;
    /**
     * 待出库
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_INBOUND = 2;
    /**
     * 待发货
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_DELIVERY = 3;
    /**
     * 待收货
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_RECEIVE = 4;
    /**
     * 已收货
     */
    public static final Integer BUSINESS_ORDER_STATUS_HAS_RECEIVED = 5;
    /**
     * 已完成
     */
    public static final Integer BUSINESS_ORDER_STATUS_FINISH = 6;
    /**
     * 已取消
     */
    public static final Integer BUSINESS_ORDER_STATUS_CANCELED = 7;
    /**
     * 申请退货待确认
     */
    public static final Integer BUSINESS_ORDER_STATUS_APPLY_RETURN_GOODS_TO_CONFIRM = 8;
    /**
     * 待退货
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_RETURN_GOODS = 9;
    /**
     * 待退货入库
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_RETURN_GOODS_INBOUND = 10;
    /**
     * 待退款
     */
    public static final Integer BUSINESS_ORDER_STATUS_WAIT_FOR_REFUND = 11;
    /**
     * 已退款
     */
    public static final Integer BUSINESS_ORDER_STATUS_FINISH_REFUND = 12;

    /************************订单退款状态***********************************/
    public static final Integer BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_CONFIRM = 1;
    public static final Integer BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_RETURN_PRODUCT = 2;
    public static final Integer BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_RETURN_INBOUND = 3;
    public static final Integer BUSINESS_ORDER_REFUND_STATUS_WAIT_FOR_REFUND_MONEY = 4;
    public static final Integer BUSINESS_ORDER_REFUND_STATUS_FINISH = 5;



    /*******************************************************************/
    public static final String SQL_MIN_PRICE_SPEC_TOTAL = "select count(distinct p1.id)";
    public static final String SQL_MIN_PRICE_SPEC_PARAMS = "select s1.id sid,s1.name sname,sp1.id spid,sp1.specification spname,p1.platform_price pPrice,sum(sp2.sale_number*sp2.has_sold)+s1.base_sale_number hasSold,min(im1.medium_path) mediumPath";
    public static final String SQL_MIN_PRICE_SPEC = " from hy_specialty_price p1,hy_specialty_specification sp1,hy_specialty s1,hy_specialty_image im1,hy_specialty_specification sp2"
            + " where p1.is_active=1 and p1.specification_id=sp1.id and sp1.specialty_id=s1.id and sp1.is_active=1 and s1.is_active=1 and s1.sale_state=1 and s1.id=im1.specialty_id and im1.is_logo=1 and sp2.specialty_id=s1.id"
            + " and sp1.id=(select min(p2.specification_id) from hy_specialty_price p2 where p2.specialty_id=s1.id and p2.platform_price=(select min(p3.platform_price) from hy_specialty_price p3 where p3.is_active=1 and p3.specialty_id=s1.id))"
            + " and exists(select *from hy_specialty_specification sp3 where sp3.id in (sp1.id,sp1.pid) and sp3.base_inbound>0)";

    public static final String SQL_MIN_PRICE_SPEC_BY_LABEL = " from hy_specialty_price p1,hy_specialty_specification sp1,hy_specialty s1,hy_specialty_image im1,hy_specialty_specification sp2,hy_specialty_label sl1" +
            " where p1.is_active=1 and p1.specification_id=sp1.id and sp1.specialty_id=s1.id and sp1.is_active=1 and s1.is_active=1 and s1.sale_state=1 and s1.id=im1.specialty_id and im1.is_logo=1 and sp2.specialty_id=s1.id" +
            " and sp1.id=(select min(p2.specification_id) from hy_specialty_price p2 where p2.specialty_id=s1.id and p2.platform_price=(select min(p3.platform_price) from hy_specialty_price p3 where p3.is_active=1 and p3.specialty_id=s1.id))" +
            " and exists(select *from hy_specialty_specification sp3 where sp3.id in (sp1.id,sp1.pid) and sp3.base_inbound>0)" +
            " and sl1.is_marked=1 and sl1.specialty_id=s1.id";
}