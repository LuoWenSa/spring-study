import com.luo.config.LuoConfig;
import com.luo.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        //如果完全使用了配置类方式去做，我们就只能通过AnnotationConfig上下文(AnnotationConfigApplicationContext)来获取容器，通过配置类的class对象加载！
        ApplicationContext context = new AnnotationConfigApplicationContext(LuoConfig.class);
        User user = (User) context.getBean("getUser");
        System.out.println("user.getName() = " + user.getName());
    }
}
