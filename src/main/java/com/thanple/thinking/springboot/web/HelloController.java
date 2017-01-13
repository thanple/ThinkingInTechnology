package com.thanple.thinking.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Thanple on 2017/1/12.
 *
 * 重新编译代码 ctrl+shift+F9
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String home() {
        return "Hello World!";
    }
}
