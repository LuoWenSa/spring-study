# Spring介绍

## 简介

**spring理念：**本身是个大杂烩，粘合剂，整合了现有的几乎所有的技术框架

**spring中文文档：**https://www.docs4dev.com/docs/zh/spring-framework/5.1.3.RELEASE/reference

**springGithub：**https://github.com/spring-projects/spring-framework

**重要依赖：**

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<!-- springWeb-mvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>6.0.11</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<!-- spring整合mybatis的jar包 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>6.0.11</version>
</dependency>

```

**优点：**

- Spring是一个开源的免费的框架（容器）！
- Spring是一个轻量级*（jar包容量小）*、非入侵式*（引入了它，不会对你其他代码引起影响）*的框架！

- <font color=red>**控制反转（IOC），面向切面编程（AOP）**</font>
- <font color="red">**支持事务的处理**</font>

==总结一句话：Spring就是一个轻量级的控制反转（IOC）和面向切面编程的（AOP）的框架==

## 组成

![](https://img2018.cnblogs.com/blog/1820931/201910/1820931-20191011104635593-1705884230.png)

1.Spring Core：Spring 核心，它是框架最基础的部分，提供 IOC 和依赖注入 DI 特性。

2.Spring Context：Spring 上下文容器，它是 BeanFactory 功能加强的一个子接口。

3.Spring Web：它提供 Web 应用开发的支持。

4.Spring MVC：它针对 Web 应用中 MVC 思想的实现。

5.Spring DAO：提供对 JDBC 抽象层，简化了 JDBC 编码，同时，编码更具有健壮性。

6.Spring ORM：它支持用于流行的 ORM 框架的整合，比如：Spring + Hibernate、Spring + iBatis、 Spring + JDO 的整合等。

7.Spring AOP：即面向切面编程，它提供了与 AOP 联盟兼容的编程实现。

# IOC

## IOC理论推导

在我们之前的业务中，用户的需求可能会影响我们原来的代码，我们需要根据用户的需求去修改原代码！如果程序代码量十分大，修改一次的成本代价十分昂贵！



**我们使用一个Set接口实现，已经发生了革命性的变化！**

```java
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

-------------------------------------------------------------------------
public class MyTest {  //模拟用户使用
    public static void main(String[] args) {
        //用户实际调用的是业务层，dao层他们不需要接触
        UserService userService = new UserServiceImpl();

        //注入思想
        ((UserServiceImpl)userService).setUserDao(new UserMysqlDaoImpl());

        userService.getUser();
    }
}
```

- **之前，程序是主动创建对象！控制权在程序员手上！**
- **使用了set注入后，程序不再具有主动性，而是变成了被动的接受对象！**

这种思想，从本质上解决了问题，我们程序员不用再去管理对象的创建了。系统的耦合性大大降低~，可以更加专注地在业务地实现上！这是IOC的原型！
