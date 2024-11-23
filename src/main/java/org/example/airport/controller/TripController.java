package org.example.airport.controller;

import jakarta.annotation.Resource;
import org.example.airport.domain.Trip;
import org.example.airport.domain.User;
import org.example.airport.repository.UserDao;
import org.example.airport.service.TripsService;
import org.example.airport.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/trip")
public class TripController {

    @Resource
    private TripsService tripService;
    @Autowired
    private UserDao userDao;

    // 新增行程
    @PostMapping("/add")
    public Result<Trip> addTrip(@RequestBody Trip trip) {
        // 使用 trip 中的 userId 查找 User 实体
        User user = userDao.findById(trip.getUserId());  // 使用 userId 获取 User 实体
        if (user == null) {
            return Result.error("无法找到用户！", "/trip/add");
        }
        trip.setUser(user);  // 设置 User 对象到 Trip 实体中
        Trip newTrip = tripService.addTrip(trip);

        if (newTrip!= null) {
            return Result.success(newTrip, "行程添加成功！", "/trip/list");
        } else {
            return Result.error("行程添加失败！", "/trip/add");
        }
    }
    // 修改行程
    @PutMapping("/update")
    public Result<Trip> updateTrip(@RequestBody Trip trip) {
        Trip updatedTrip = tripService.updateTrip(trip);
        if (updatedTrip!= null) {
            return Result.success(updatedTrip, "行程更新成功！", "/trip/list");
        } else {
            return Result.error("行程更新失败！", "/trip/list");
        }
    }
    // 删除行程
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteTrip(@PathVariable int id) {
        Trip deletedTrip = tripService.deleteTrip(id);
        if (deletedTrip!= null) {
            return Result.success("行程删除成功！", "/trip/delete");
        } else {
            return Result.error("行程删除失败！", "/trip/delete");
        }
    }
    // 模糊查询行程
    @GetMapping("/search")
    public Result<Page<Trip>> searchTrips(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "3") int size,
                                          @RequestParam String query) {// 每页显示的记录数
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> trips = tripService.searchTrips(query, pageable);
        if (!trips.isEmpty()) {
            return Result.success(trips, "行程查询成功！", "/trip/searchResults");
        } else {
            return Result.error("未找到符合条件的行程！", "/trip/list");
        }
    }
}
