package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.entity.User;
import com.thanple.thinking.springboot.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Thanple on 2017/2/24.
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertUser(){
        User user = new User();
        user.setName("Tom");
        user.setEmail("805685524@qq.com");

        userRepository.save(user);
    }

}
