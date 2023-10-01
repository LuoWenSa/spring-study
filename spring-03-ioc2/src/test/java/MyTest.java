import com.luo.pojo.User;
import com.luo.pojo.UserT;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
//        User user = new User();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //验证是否是单例模式，ans：是
        //User user = (User) context.getBean("user");
        //User user2 = (User) context.getBean("user");
        //user.show();
        //System.out.println(user == user2);

        //验证别名alias
        //User user = (User) context.getBean("userAlias");
        //user.show();

        //起别名的另一种方式,bean中的name标签
        //UserT userT2 = (UserT) context.getBean("userT2");
        UserT u3 = (UserT) context.getBean("u3");
        u3.show();

    }
}
