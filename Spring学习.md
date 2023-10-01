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

## IOC本质

**控制反转IOC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法，**没有IOC的程序中，我们使用面向对象编程，对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，

控制反转后将对象的创建转移给第三方，个人认为所谓的控制反转就是：获得依赖对象的方式反转了。

![](https://img-blog.csdnimg.cn/8b90e173eb9b4f279e99fca07022d0d9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Ya35LiBXw==,size_20,color_FFFFFF,t_70,g_se,x_16)

**控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在spring中实现控制反转的是IOC容器，其实现方法是依赖注入（Dependency Injection,DI）。**



- 控制：谁来控制对象的创建，传统应用程序的对象是由程序本身控制创建的，使用spring后，对象是由spring创建的。

- 反转：程序本身不创建对象，而变成被动的接收对象。

- <font color="red">依赖注入：就是利用set方法来进行注入的。</font>

IOC是一种编程思想，由主动的编程变成被动的接收。

## Spring中IOC

### 基础

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--使用spring来创建对象，在spring这些都称为Bean
    类型 变量名 = new 类型();
    Hello hello = new Hello();

    id = 变量名
    class = new 的对象;
    property 相当于给对象中的属性设置一个值
    -->
    <bean id="hello" class="com.luo.pojo.Hello">
        <property name="str" value="spring"/>
    </bean>

    <!-- more bean definitions go here -->

</beans>
```

```java
public class MyTest {
    public static void main(String[] args) {
        //获取spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //我们的对象现在都在spring中管理了，我们要使用，直接去里面取出来就可以了！
        Hello hello = (Hello) context.getBean("hello");
        System.out.println("hello.toString() = " + hello);
    }
}
```

### 进阶

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userMysqlDaoImpl" class="com.luo.dao.UserMysqlDaoImpl"></bean>
    <bean id="userOracleDaoImpl" class="com.luo.dao.UserOracleDaoImpl"></bean>

    <bean id="userService" class="com.luo.service.UserServiceImpl">
        <!--ref : 引用Spring容器中创建好的对象
            value : 具体的值，基本数据类型！
        -->
        <property name="userDao" ref="userOracleDaoImpl"/>
    </bean>
</beans>
```

```java
public class MySpringTest {
    public static void main(String[] args) {
        //获取ApplicationContext：拿到spring的容器
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //容器在手，天下我有，需要什么，就直接get什么！
        UserService userService = (UserServiceImpl) context.getBean("userService");
        userService.getUser();
    }
}
```

## IOC创建对象的方式

1.使用无参构造创建对象，默认！

2.使用有参构造创建对象

​	1.下标赋值

```xml
<!--构造器注入-下标方式-->
<bean id="user" class="com.luo.pojo.User">
    <constructor-arg index="0" value="xiaoluo"/>
</bean>
```

​	2.类型

```xml
<!--构造器注入-类型方式-->
<bean id="user" class="com.luo.pojo.User">
    <constructor-arg type="java.lang.String" value="小罗"/>
</bean>
```

​	3.参数名

```xml
<!--构造器注入-参数名方式-->
<bean id="user" class="com.luo.pojo.User">
    <constructor-arg name="name" value="xiaoluo"/>
</bean>
```

​	4.引用

```xml-dtd
<beans>
    <bean id="thingOne" class="x.y.ThingOne">
        <constructor-arg ref="thingTwo"/>
        <constructor-arg ref="thingThree"/>
    </bean>

    <bean id="thingTwo" class="x.y.ThingTwo"/>

    <bean id="thingThree" class="x.y.ThingThree"/>
</beans>
```

总结：在配置文件加载的时候，容器中管理的对象就已经初始化了！

## Spring配置

### 1.别名、Bean的配置

本名和别名都可以通过context.getBean获取到实例对象

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

<!--构造器注入-参数名方式-->
    <bean id="user" class="com.luo.pojo.User">
        <constructor-arg name="name" value="xiaoluo"/>
    </bean>

    <bean id="userT" class="com.luo.pojo.UserT">

    </bean>

<!-- 取一个别名 alias一般不用-->
    <alias name="user" alias="userAlias"/> 

</beans>
```

**常用方式(bean中的name标签)：**

```xml-dtd
<!--
    id：bean的唯一标识符，也就是相当于我们学的对象名
    class：bean对象对应的全限定名：包名 + 类型
    name：也是别名，而且name，可以同时取多个别名
-->
<bean id="userT" class="com.luo.pojo.UserT" name="userT2, u2">

</bean>
```

### 3.import

这个import，一般用于团队开发使用，他可以将多个配置文件，导入合并为一个

**遇到不同的xml中有相同id的bean，spring会选一个进行加载，不会产生有id的冲突**

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="beans.xml"/>
    <import resource="beans2.xml"/>
    <import resource="beans3.xml"/>

</beans>
```

## 依赖注入(DI)

#### 1.构造器注入（constructor-arg）

前面已经说过了

#### 2.Set方式注入【重点】（property）



#### 3.拓展方式注入

其他方式。。。