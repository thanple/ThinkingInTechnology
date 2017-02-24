package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.jms.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Thanple on 2017/2/24.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsTest {

    @Autowired
    private Sender sender;

    @Test
    public void sendMsg(){
        sender.sendMail();
    }

    @Test
    public void sendMsgs() throws InterruptedException {
        for(int i=0; i<100; i++){
            sender.sendMail();
        }
    }

}
