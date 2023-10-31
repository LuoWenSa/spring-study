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
  
- 基于类的动态代理
  
  ​	基于类：cglib
  
  - java字节码实现：javasist

动态代理比静态代理多的好处：

- **一个动态代理类代理的是一个接口**，一般就是对应一类业务
- 一个动态代理类可以代理多个类，只要实现了同一个**接口**即可！

