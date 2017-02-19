package com.thanple.thinking.springboot.autowireds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thanple on 2017/2/19.
 */
@Service
public class StrategyCotext {

    //依赖注入集合类型
    @Autowired
    private List<IStrategy> strategyList;

    public void runStrategy(){
        for(IStrategy strategy : strategyList){
            strategy.run();
        }
    }

}
