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

}