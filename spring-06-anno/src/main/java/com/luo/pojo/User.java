package com.luo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//等价于 <bean id="user" class="com.luo.pojo.User"/>
//@Component 组件

@Component
@Scope("singleton") //singleton单例，prototype原型
public class User {

    //相当于 <property name="name" value="罗文飒"/>
    @Value("罗文飒")
    public String name;

//    @Value("罗文飒")
    public void setName(String name) {
        this.name = name;
    }
}
