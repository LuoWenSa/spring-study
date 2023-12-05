package com.luo.mapper;

import com.luo.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUsers();

    //添加一个用户
    int addUser(User user);

    //删除一个用户
    int deleteUser(int id);

    int test(User user, int id);
}
