package com.luo.demo04;

import com.luo.demo02.UserService;
import com.luo.demo02.UserServiceImpl;
import com.luo.demo02.UserServiceImpl2;

public class Client {
    public static void main(String[] args) {
        //真实角色
        UserService userService = new UserServiceImpl();
        //代理角色
        //1.获取调用处理程序
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //2.设置要代理的对象
        pih.setTarget(userService);
        //3.动态生成代理类
        UserService proxy = (UserService) pih.getProxy();
        proxy.add();
    }
}
