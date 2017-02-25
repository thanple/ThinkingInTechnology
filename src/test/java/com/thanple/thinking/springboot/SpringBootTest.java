package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.entity.User;
import com.thanple.thinking.springboot.mybatis.UserMapper;
import com.thanple.thinking.springboot.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Thanple on 2017/2/24.
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUser(){
        User user = new User();
        user.setName("Tom");
        user.setEmail("805685524@qq.com");

        userRepository.save(user);
    }

    @Test
    public void testInsertEmail(){
        userMapper.insert("UserMapper1","1231231@qq.com");

        List<User> userList = userMapper.find();
        System.out.println(userList);
    }

}
