package com.thanple.thinking.springboot.jms;

import com.thanple.thinking.springboot.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Thanple on 2017/2/24.
 */
@Component
public class Receiver {
    Logger log = LoggerFactory.getLogger(Receiver.class);

    //消息监听
    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        log.info("Received <{}>",email);
    }

}