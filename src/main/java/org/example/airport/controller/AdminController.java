package org.example.airport.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.airport.domain.Admin;
import org.example.airport.repository.AdminDao;
import org.example.airport.service.AdminService;
import org.example.airport.utils.Result;
import org.example.airport.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.airport.utils.Result.success;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private TokenUtils tokenUtils;
    @Autowired
    private AdminDao adminDao;

    // 登录
    @PostMapping("/login")
    public Result<String> loginController(@RequestParam("username") String username, @RequestParam("password") String password,
                                          HttpServletResponse response, HttpSession session,
                                          HttpServletRequest request){
        // 执行登录验证
        Admin admin = adminService.loginService(username, password);
        if (admin!= null) {
            // 登录成功，将用户信息存入 session 中
            session.setAttribute("loggedInUser", admin.getAdminName());
            // 登录成功，生成长令牌并存储在 cookie 中
            String longToken = tokenUtils.generateLongToken(adminDao.findByAdminName(username).getAdminName());
            // Cookie 为键值对数据格式
            Cookie cookie_longToken = new Cookie("cookie_longToken", longToken);
            cookie_longToken.setMaxAge(3600 * 24 * 7); // 设置 Cookie 过期时间为 1 周
            // 表示当前项目下都携带这个cookie
            cookie_longToken.setPath("/");
            // 添加到 response 中
            response.addCookie(cookie_longToken);

            // 登录成功，重定向到 /user/home
            return success("admin", "管理员登录成功","/admin/home");
        } else {
            return Result.error("账号或密码错误","/admin/login");
        }
    }
    // 退出登录
    @GetMapping(value = "/logout")
    public Result logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("loggedInUser");
        // 删除cookie，实现退出登录
        Cookie cookie_longToken = new Cookie("cookie_longToken", "");
        // 设置cookie的持久化时间，0
        cookie_longToken.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_longToken.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_longToken);
        return success("退出成功！","/admin/login");
    }
    // 注册
    @PostMapping("/register")
    public Result<Admin> registController(@RequestBody Admin newadmin) {
        // 执行用户注册
        Admin admin = adminService.registerService(newadmin);
        // 根据注册结果返回适当的响应
        if (admin != null) {
            return Result.success(admin,"注册成功！","/admin/home");
        } else {
            return Result.error("用户名已存在！", "/admin/register");
        }
    }
}

