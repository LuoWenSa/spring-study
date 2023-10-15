import com.luo.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = context.getBean("user", User.class);
        System.out.println("user.name = " + user.name);

        //验证@Scope注解
//        User user2 = context.getBean("user", User.class);
//        System.out.println("flag = " + (user == user2));
    }
}
