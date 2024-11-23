package org.example.airport.service.servicelmpl;

import jakarta.annotation.Resource;
import org.example.airport.domain.User;
import org.example.airport.repository.UserDao;
import org.example.airport.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicelmpl implements UserService {
    @Resource
    private UserDao userDao;
    // 查
    @Override
    public Page<User> searchService(String query, Pageable pageable) {
        return userDao.findByFuzzyQuery(query, pageable);
    }
    // 增：注册
    @Override
    public User registerService(User user) {
        if (userDao.findByUserName(user.getUserName())!= null) {
            return null;
        } else {
            User newUser = userDao.save(user);
            if (newUser!= null) {
                newUser.setUserPassword("");
            }
            return newUser;
        }
    }
    // 改：修改信息
    @Override
    public User updateService(User user) {
        User oldUser = userDao.findById(user.getId());
        if (oldUser!= null) {
            oldUser.setUserName(user.getUserName());
            oldUser.setUserPassword(user.getUserPassword());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setEmailAddress(user.getEmailAddress());
            oldUser.setGender(user.getGender());
            oldUser.setBirthday(user.getBirthday());
            userDao.save(oldUser);
        }
        return userDao.save(oldUser);//save方法既能实现新增，也能实现修改
    }
    // 删：删除用户
    @Override
    public User deleteService(int id) {
        User userToDelete = userDao.findById(id);
        if (userToDelete!= null) {
            userDao.delete(userToDelete);
            return userToDelete;
        } else {
            return null;
        }
    }
    // 查：查询所有用户
    @Override
    public List<User> getAllService() {
        return userDao.findAll();
    }
}