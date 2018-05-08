package com.thanple.thinking.springboot.web;

import com.thanple.thinking.springboot.entity.User;
import com.thanple.thinking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        List<String> nameRepository = Arrays.asList("Tom","Cindy","Linda","Jack","Rose");

        User commonFriend = userRepository.findOne(1L);

        User user = new User();
        user.setName(nameRepository.get((int)(Math.random()*nameRepository.size())) + "" +String.format("%04d",(int)(Math.random() * 1000 )) );
        user.setEmail(String.format("%4d@qq.com",(int)(Math.random() * 100000000) ));
        user.setCreateTime(new Date());
        if(commonFriend != null) {
            user.getFriends().add(commonFriend);
        }

        userRepository.save(user);
        return user;
    }
}
