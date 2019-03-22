package com.zmc.springcloud.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
public final class WebUtils {
    /**
     * 不可实例化
     */
    private WebUtils() {
    }

    /**
     * 添加cookie
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     * @param value
     *            cookie值
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
