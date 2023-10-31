package com.luo.demo02;

public class Client {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //直接使用真实角色
        //userService.add();

        //使用代理，增加日志输出
        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setUserService(userService);
        proxy.query();
    }
}
