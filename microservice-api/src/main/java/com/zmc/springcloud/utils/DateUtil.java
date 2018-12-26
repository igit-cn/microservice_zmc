package com.zmc.springcloud.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**日期 时间相关的工具类
 *
 * Created by xyy on 2018/12/11.
 *
 * @author xyy
 */
public  class DateUtil {
    /** 将形如"2018-12-10T07:58:34.000Z"的字符串转为java.util.Date*/
    public static Date stringToDate(String str) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(str.substring(0,10) + " " + str.substring(11, 19));
    }
}
