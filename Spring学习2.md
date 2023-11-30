# 10、代理模式

为什么要学习代理模式？因为这就是SpringAOP的底层！【SpringAOP 和 SpringMVC】

代理模式的分类：

- 静态代理

- 动态代理

  ​															租房

​																      |

​                                                --------------------------------------

​												|					                        |			

真实的人-------------------->代理角色---------------------->真实的角色

(你，租房的人)                 (中介)                                   (房东)



## 10.1、静态代理

角色分析：

- 抽象角色：一般会使用接口或者抽象类来解决
- 真实角色：被代理的角色
- 代理角色：代理真实角色，代理真实角色后，我们一般会做一些附属操作
- 客户：访问代理对象的人！



代码步骤：

​	1.接口

```java
//租房
public interface Rent {

    void rent();

}
```

​	2.真实角色

```java
//房东
public class Host implements Rent{
    @Override
    public void rent() {
        System.out.println("房东要出租房子！");
    }
}
```

​	3.代理角色

```java
//代理-中介
public class Proxy implements Rent{

    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    @Override
    public void rent() {
        seeHouse();
        host.rent();
        hetong();
        fee();
    }

    //看房
    public void seeHouse(){
        System.out.println("中介带你看房！");
    }

    //签租赁合同
    public void hetong(){
        System.out.println("签租赁合同！");
    }

    //收中介费
    public void fee(){
        System.out.println("收中介费！");
    }
}
```

​	4.客户端访问代理角色

```java
//租客
public class Client {
    public static void main(String[] args) {
        Host host = new Host();
        //1.直接找房东租房子
        //host.rent();
        //2.找代理租房子
        //代理，中介帮房东租房子，但是呢？代理角色一般会有一些附属操作！
        Proxy proxy = new Proxy(host);

        //你不用面对房东，直接找中介租房即可！
        proxy.rent();
    }
}
----------------------------------------------------------------
中介带你看房！
房东要出租房子！
签租赁合同！
收中介费！
```

代理模式的好处：

- 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
- 公共也就交给了代理角色！实现了业务的分工！
- 公共业务发生扩展的时候，方便集中管理！

缺点：

- 一个真实角色就会产生一个代理角色；代码量会翻倍，开发效率会变低

## 10.2、加深理解

代码步骤：

​	1.接口

```java
public interface UserService {
     void add();
     void delete();
     void update();
     void query();
}
```

​	2.真实角色

```java
//真实对象
public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("增加了一个用户");
    }

    @Override
    public void delete() {
        System.out.println("删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("修改了一个用户");
    }

    @Override
    public void query() {
        System.out.println("查询了一个用户");
    }

    //1.改动原有的业务代码，在公司中是大忌！
}
```

​	3.代理角色

```java
public class UserServiceProxy implements UserService{

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add() {
        log("add");
        userService.add();
    }

    @Override
    public void delete() {
        log("delete");
        userService.delete();
    }

    @Override
    public void update() {
        log("update");
        userService.update();
    }

    @Override
    public void query() {
        log("query");
        userService.query();
    }

    //日志方法
    public void log(String msg){
        System.out.print("[Debug] 使用了"+msg+"方法:");
    }
}
```

​	4.客户端访问代理角色

```java
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
----------------------------------------------------------
[Debug] 使用了query方法:查询了一个用户
```

## 10.3、动态代理

- 动态代理和静态代理角色一样

- 动态代理的代理类是动态生成的，不是我们直接写好的！

- 动态代理分为**两大类**：

  - 基于接口的动态代理

    ​	基于接口--JDK动态代理

    ​	需要了解两个类：Proxy：代理，InvocationHandler：调用处理程序

    ​	代理角色：

    ```java
    //等会我们会用这个类，自动生成代理类！
    public class ProxyInvocationHandler implements InvocationHandler {
    
        //被代理的接口
        private Object target;
    
        public void setTarget(Object target) {
            this.target = target;
        }
    
        //生成得到代理类
        public Object getProxy(){
            return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                   target.getClass().getInterfaces(),this);
        }
    
    
        //处理代理实例，并返回结果
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //动态代理的本质，就是使用反射机制实现！
            return method.invoke(target, args);
        }
    
    }
    ```

    客户端访问代理角色:

    ```java
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
    ```
  ```
  
  ```
  
- 基于类的动态代理
  
  ​	基于类：cglib
  
  - java字节码实现：javasist

动态代理比静态代理多的好处：

- **一个动态代理类代理的是一个接口**，一般就是对应一类业务
- 一个动态代理类可以代理多个类，只要实现了同一个**接口**即可！

# 11、AOP

## 11.1 什么是AOP

在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。AOP是[OOP](https://baike.baidu.com/item/OOP/1152915?fromModule=lemma_inlink)的延续，是[软件开发](https://baike.baidu.com/item/软件开发/3448966?fromModule=lemma_inlink)中的一个热点，也是[Spring](https://baike.baidu.com/item/Spring/0?fromModule=lemma_inlink)框架中的一个重要内容，是[函数式编程](https://baike.baidu.com/item/函数式编程/4035031?fromModule=lemma_inlink)的一种衍生范型。利用AOP可以对[业务逻辑](https://baike.baidu.com/item/业务逻辑/3159866?fromModule=lemma_inlink)的各个部分进行隔离，从而使得业务逻辑各部分之间的[耦合度](https://baike.baidu.com/item/耦合度/2603938?fromModule=lemma_inlink)降低，提高程序的[可重用性](https://baike.baidu.com/item/可重用性/53650612?fromModule=lemma_inlink)，同时提高了开发的效率。

![](https://img-blog.csdnimg.cn/20200219154244873.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDkyNzQzNg==,size_16,color_FFFFFF,t_70)

## 11.2 AOP在Spring中的作用

==提供声明式事务；允许用户自定义切面==

- 横切关注点：跨越引用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但我们需要关注的部分，就是横切关注点，如日志，安全，缓存，事务等......

- 切面(ASPECT)：横切：关注点被关注点模块化的特殊对象，即，它是一个类。

- 通知(Advice)：切面必须要完成的工作，即，它是类中的一个方法。

- 目标(Target)：被通知对象.

- 代理(Proxy)：向目标对象对应通知之后创建的对象。

- 切入点(PointCut)：切面通知执行的“地点”的定义

- 连接点(JointPoint)：与切入点匹配的执行点

![](https://pic3.zhimg.com/80/v2-92bdc7da1ee07b35563af903d885d9d2_720w.webp)



## 11.3 使用Spring实现AOP

【重点】使用AOP织入，需要导入一个依赖包！

```xml
<!--https://mvnrepository.com/artifact/org.aspectj/aspectjweaver-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```



测试类，以下三种方式的测试都一样，不再赘述

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //动态代理代理的是接口：注意点
        UserService userService = (UserService) context.getBean("userService");

        userService.add();
    }
}
```



### 方式一：使用Spring的API接口【主要是SpringAPI接口实现】

```xml
<!--方式一：使用原生Spring API接口-->
<!--配置aop:需要导入aop的约束-->
<aop:config>
    <!--切入点： expression：表达式，execution(要执行的位置！(*(修饰词) *(返回值) *(类名) *(方法名) *(参数)))-->
    <aop:pointcut id="pointcut" expression="execution(* com.luo.service.UserServiceImpl.*(..))"/>

    <!--执行环绕增加-->
    <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
    <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>

</aop:config>
```

```java
public class Log implements MethodBeforeAdvice {

    //method：要执行的目标对象的方法
    //args：参数
    //target：目标对象
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName() + "的" + method.getName() + "被执行了");
    }
}
```

```java
public class AfterLog implements AfterReturningAdvice {

    //returnValue：返回值
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了" + method.getName() + "方法，返回结果为：" + returnValue);
    }
}
```

### 方式二：使用自定义类来实现AOP【主要是切面定义】

```xml
<!--方式二：自定义类-->
<bean id="diy" class="com.luo.diy.DiyPointCut"/>
<aop:config>
    <!--自定义切面，ref 要引用的类-->
    <aop:aspect ref="diy">
        <!--切入点-->
        <aop:pointcut id="point" expression="execution(* com.luo.service.UserServiceImpl.*(..))"/>
        <!--通知-->
        <aop:before method="before" pointcut-ref="point"/>
        <aop:after method="after" pointcut-ref="point"/>
    </aop:aspect>
</aop:config>
```

```java
public class DiyPointCut {

    public void before(){
        System.out.println("==========方法执行前==========");
    }

    public void after(){
        System.out.println("==========方法执行后==========");
    }
}
```

### 方式三：使用注解实现！

```xml
<!--方式三：使用注解实现-->
<bean id="annotationPointCut" class="com.luo.diy.AnnotationPointCut"/>
<!--开启注解支持！   JDK(默认 proxy-target-class="false") cglib(proxy-target-class="true")-->
<aop:aspectj-autoproxy proxy-target-class="false"/>
```

```java
@Aspect //标记这个类是一个切面
public class AnnotationPointCut {

    @Before("execution(* com.luo.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("=====方法执行前=====");
    }

    @After("execution(* com.luo.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("=====方法执行后=====");
    }

    //在环绕增强中，我们可以给定一个参数，代表我们要获取处理切入的点：
    @Around("execution(* com.luo.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");

        //执行方法
        Object proceed = jp.proceed();

        System.out.println("环绕后");
    }

}
```

