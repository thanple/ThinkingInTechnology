package com.thanple.thinking.springboot.web;

import com.thanple.thinking.springboot.entity.User;
import com.thanple.thinking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Thanple on 2017/2/23.
 */

@RestController
@RequestMapping("/jpa")
public class RepoDemoController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/insertUser")
    public User insertUser() {
        User user = new User();
        user.setName("Tom");
        user.setEmail("805685524@qq.com");

        userRepository.save(user);

        return user;
    }
}
