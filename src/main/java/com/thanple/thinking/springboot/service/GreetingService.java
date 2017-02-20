package com.thanple.thinking.springboot.service;

import com.thanple.thinking.springboot.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thanple on 2017/2/20.
 */

@Service
public class GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;


}
