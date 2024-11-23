package org.example.airport.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.airport.utils.TokenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    //管理员
    @GetMapping("/admin/login")
    public String loginPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "登录页面");
        return "adminLogin"; // 这里返回的是Thymeleaf模板的文件名，比如 adminLogin.html
    }
    @GetMapping("/admin/register")
    public String registerPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "注册页面");
        return "adminRegister"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    @GetMapping("/admin/home")
    public String home(HttpServletRequest request, Model model) {
        // 获取长令牌 Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                if ("cookie_longToken".equals(cookie.getName())) {
                    String longToken = cookie.getValue();
                    String username = TokenUtils.getUsernameFromLongToken(longToken);
                    if (username!= null) {
                        model.addAttribute("welcomeMessage", "欢迎回来，" + username);
                        break;
                    }
                }
            }
        }
        return "adminHome";
    }
    // 用户添加页面
    @GetMapping("/user/add")
    public String addPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "user增");
        return "userAdd"; // 这里返回的是Thymeleaf模板的文件名，比如 adminLogin.html
    }
    // 用户删除页面
    @GetMapping("/user/delete")
    public String deletePage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "user删");
        return "userDelete"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    // 用户更新页面
    @GetMapping("/user/update")
    public String updatePage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "user改");
        return "userUpdate"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    // 用户查询页面
    @GetMapping("/user/search/view")
    public String searchPage(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "user查");
        return "userSearch"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    // 行程添加页面
    @GetMapping("/trip/add")
    public String addPage2(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "trip增");
        return "tripAdd"; // 这里返回的是Thymeleaf模板的文件名，比如 adminLogin.html
    }
    // 行程删除页面
    @GetMapping("/trip/delete")
    public String deletePage2(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "trip删");
        return "tripDelete"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    // 行程更新页面
    @GetMapping("/trip/update")
    public String updatePage2(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "trip改");
        return "tripUpdate"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
    // 行程查询页面
    @GetMapping("/trip/search/view")
    public String searchPage2(Model model) {
        // 可以向模板传递需要的数据
        model.addAttribute("pageTitle", "trip查");
        return "tripSearch"; // 这里返回的是Thymeleaf模板的文件名，比如 adminRegister.html
    }
}
