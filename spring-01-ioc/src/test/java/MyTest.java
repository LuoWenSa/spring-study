import com.luo.dao.UserMysqlDaoImpl;
import com.luo.service.UserService;
import com.luo.service.UserServiceImpl;

public class MyTest {
    public static void main(String[] args) { //模拟用户使用
        //用户实际调用的是业务层，dao层他们不需要接触
        UserService userService = new UserServiceImpl();

        //注入思想
        ((UserServiceImpl)userService).setUserDao(new UserMysqlDaoImpl());

        userService.getUser();
    }
}
