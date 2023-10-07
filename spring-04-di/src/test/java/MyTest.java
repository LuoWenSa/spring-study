import com.luo.pojo.Student;
import com.luo.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student");
        System.out.println("student = " + student);
    }

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeans.xml");
//        User user = context.getBean("user", User.class);
//        User user1 = context.getBean("user", User.class);
//        System.out.println(user == user1);
        User user2 = context.getBean("user2", User.class);
        System.out.println("user2 = " + user2);
    }

}
