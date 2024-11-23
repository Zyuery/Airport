package org.example.airport.service.servicelmpl;

import jakarta.annotation.Resource;
import org.example.airport.domain.Admin;
import org.example.airport.repository.AdminDao;
import org.example.airport.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServicelmpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Override
    public Admin loginService(String username, String password) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        Admin admin = adminDao.findByAdminNameAndAdminPassword(username, password);
        // 重要信息置空
        if(admin != null){
            admin.setAdminPassword("");
        }
        return admin;
    }

    @Override
    public Admin registerService(Admin admin) {
        //当新用户的用户名已存在时
        if(adminDao.findByAdminName(admin.getAdminName())!=null){
            // 无法注册
            return null;
        }else{
            //返回创建好的用户对象(带uid)
            return adminDao.save(admin);
        }
    }
}
