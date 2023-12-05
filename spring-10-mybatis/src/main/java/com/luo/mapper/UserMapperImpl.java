package com.luo.mapper;

import com.luo.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMapperImpl implements UserMapper{

    //我们的所有操作，都使用sqlSession来执行，在原来，现在都使用SqlSessionTemplate;
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> selectUsers() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.selectUsers();
    }
}
