package org.example.airport.utils;


import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.airport.repository.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter", urlPatterns = {"/*"})
public class AutoLoginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AutoLoginFilter.class);
    private static final String EXCLUDE_ROUTE_1 = "/admin/login";
    private static final String EXCLUDE_ROUTE_2 = "/admin/logout";
    private static final String EXCLUDE_ROUTE_3 = "/admin/register";

    @Resource
    private AdminDao adminDao;

    private static final String COOKIE_LONGTOKEN_KEY = "cookie_longToken";

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("调用了 Init 方法");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        // 获取当前请求的路径
        String requestPath = ((HttpServletRequest) servletRequest).getRequestURI();

        // 判断当前请求路径是否为要排除的特定路由
        if (EXCLUDE_ROUTE_1.equals(requestPath) || EXCLUDE_ROUTE_2.equals(requestPath)||EXCLUDE_ROUTE_3.equals(requestPath)) {
            // 如果是要排除的路由，直接放行请求，让请求继续向下传递
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (session!= null && session.getAttribute("loggedInUser")!= null) {
            logger.info("已登录，直接继续处理请求");
            filterChain.doFilter(request, response);
        } else {
            logger.info("未登录，检查是否有自动登录的 Cookie");
            Cookie[] cookies = request.getCookies();
            if (cookies!= null) {
                for (Cookie cookie : cookies) {
                    if (COOKIE_LONGTOKEN_KEY.equals(cookie.getName())) {
                        String longTokenFromCookie = cookie.getValue();
                        String usernameFromToken = getUserUsernameFromToken(longTokenFromCookie);
                        if (usernameFromToken!= null) {
                            boolean isUserValid = validateUserFromDatabase(longTokenFromCookie);
                            if (isUserValid) {
                                session = request.getSession();
                                session.setAttribute("loggedInUser", usernameFromToken);
                                filterChain.doFilter(request, response);
                                return;
                            }
                        }
                    }
                }
            }
            response.sendRedirect("/admin/login");
        }
    }

    private String getUserUsernameFromToken(String longToken) {
        try {
            return TokenUtils.getUsernameFromLongToken(longToken);
        } catch (Exception e) {
            logger.error("从长令牌获取用户名失败", e);
            return null;
        }
    }

    private boolean validateUserFromDatabase(String longToken) {
        try {
            String username = getUserUsernameFromToken(longToken);
            if (username!= null) {
                return TokenUtils.validateLongToken(longToken, username) && adminDao.existsByAdminName(username);
            }
            return false;
        } catch (Exception e) {
            logger.error("数据库查询用户信息失败", e);
            return false;
        }
    }

    @Override
    public void destroy() {
        logger.info("调用了 Destroy 方法");
    }
}
