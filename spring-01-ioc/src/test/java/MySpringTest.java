import com.luo.service.UserService;
import com.luo.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySpringTest {
    public static void main(String[] args) {
        //获取ApplicationContext：拿到spring的容器
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //容器在手，天下我有，需要什么，就直接get什么！
        UserService userService = (UserServiceImpl) context.getBean("userService");
        userService.getUser();
    }
}
