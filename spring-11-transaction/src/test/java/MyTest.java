import com.luo.mapper.UserMapper;
import com.luo.mapper.UserMapperImpl;
import com.luo.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

        List<User> userList = userMapper.selectUsers();
        System.out.println("userList = " + userList);
    }

    @Test
    public void test2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

        User user = new User(6,"汪汪", "611");

//        //增加用户
//        userMapper.addUser(user);
//        //删除用户一定会报错，测试事务
//        userMapper.deleteUser(6);
        userMapper.test(user,6);
    }
}
