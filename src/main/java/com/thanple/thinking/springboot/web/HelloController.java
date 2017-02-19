package com.thanple.thinking.springboot.web;

import com.thanple.thinking.springboot.autowireds.StrategyCotext;
import com.thanple.thinking.springboot.entity.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Thanple on 2017/1/12.
 *
 * 重新编译代码 ctrl+shift+F9
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * Helloworld
     */
    @RequestMapping("")
    public String home() {
        return "Hello,World!";
    }


    /**
     * Rest风格demo
     */
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    /**
     * 依赖注入
     */
    @Autowired
    private StrategyCotext strategyCotext;
    @RequestMapping("/testAutowireds")
    public void testAutowireds() {
        strategyCotext.runStrategy();
    }
}
