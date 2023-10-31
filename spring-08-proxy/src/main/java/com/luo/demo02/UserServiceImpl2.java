package com.luo.demo02;

public class UserServiceImpl2 implements UserService{
    @Override
    public void add() {
        System.out.println("用第二种方式，增加了一个用户");
    }

    @Override
    public void delete() {
        System.out.println("用第二种方式，删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("用第二种方式，修改了一个用户");
    }

    @Override
    public void query() {
        System.out.println("用第二种方式，查询了一个用户");
    }
}
