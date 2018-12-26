package com.zmc.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

/**
 * 自定义Filter
 *
 * Created by xyy on 2018/12/20.
 *
 * @author xyy
 */
public class MyFilter extends ZuulFilter {

    /**
     * 是否应该执行该过滤器，如果是false，则不执行该filter
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器类型
     * 顺序: pre ->routing -> post ,以上3个顺序出现异常时都可以触发error类型的filter
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }


    /**
     * 同filterType类型中，order值越大，优先级越低
     */
    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

//        HttpServletRequest request = ctx.getRequest();
//        HttpSession session = request.getSession();

//        HttpServletResponse response = ctx.getResponse();
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
//        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
//        response.addHeader("Access-Control-Allow-Origin", "http://myzuul.com");
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");

        return null;
    }
}
