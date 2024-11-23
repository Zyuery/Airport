package org.example.airport.controller;

import jakarta.annotation.Resource;
import org.example.airport.domain.User;
import org.example.airport.service.UserService;
import org.example.airport.utils.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllService();
    }
    // 注册（增）
    @PostMapping("/add")
    public Result<User> register(@RequestBody User user) {
        User newUser = userService.registerService(user);
        if (newUser!= null) {
            return Result.success(newUser,"用户注册成功", "/admin/home");
        } else {
            return Result.error("用户名已存在！","/user/register");
        }
    }
    // 模糊查询行程
    @GetMapping("/search")
    public Result<Page<User>> searchTrips(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "3") int size,
                                          @RequestParam String query) {// 每页显示的记录数
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.searchService(query, pageable);
        if (!users.isEmpty()) {
            return Result.success(users, "用户查询成功！", "/user/searchResults");
        } else {
            return Result.error("未找到符合条件的用户！", "/user/searchResults");
        }
    }

    // 修改（改）
    @PutMapping("/update")
    public Result<User> update(@RequestBody User user) {
        User updatedUser = userService.updateService(user);
        if (updatedUser!= null) {
            return Result.success(updatedUser, "用户信息更新成功！", "/admin/home");
        } else {
            return Result.error( "用户信息更新失败！","/admin/home");
        }
    }
    // 删除（删）
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable int id) {
        User deletedUser = userService.deleteService(id);
        if (deletedUser!= null) {
            return Result.success("用户删除成功！", "/admin/home");
        } else {
            return Result.error( "用户删除失败！","/admin/home");
        }
    }
}
