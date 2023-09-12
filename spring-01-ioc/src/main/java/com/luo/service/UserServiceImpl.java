package com.luo.service;

import com.luo.dao.UserDao;
import com.luo.dao.UserDaoImpl;
import com.luo.dao.UserMysqlDaoImpl;
import com.luo.dao.UserOracleDaoImpl;

public class UserServiceImpl implements UserService {
    //1.程序控制对象，手动new Ipml才能用不用的Dao层业务
//    private UserDao userDao = new UserDaoImpl();
//    private UserDao userDao = new UserMysqlDaoImpl();
//    private UserDao userDao = new UserOracleDaoImpl();

    private UserDao userDao;

    //利用set进行动态实现值的注入！
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
