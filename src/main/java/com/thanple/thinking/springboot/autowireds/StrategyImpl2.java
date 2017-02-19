package com.thanple.thinking.springboot.autowireds;

import org.springframework.stereotype.Service;

/**
 * Created by Thanple on 2017/2/19.
 */
@Service
public class StrategyImpl2 implements IStrategy{
    @Override
    public void run() {
        System.out.println("Strategy2 run");
    }
}
