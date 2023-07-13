package org.example.takeaway.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.takeaway.common.BaseContext;
import org.example.takeaway.common.R;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
@Component
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = check(urls, requestURI);
        if(check){
            filterChain.doFilter(request, response);
            return;
        }
        /*后端登录*/
        if(request.getSession().getAttribute("employee") != null){

            Long Id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(Id);

            filterChain.doFilter(request, response);
            return;
        }
        /*移动端登陆*/
        if(request.getSession().getAttribute("user") != null){

            Long Id = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(Id);

            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match == true){
                return true;
            }
        }
        return false;
    }
}
