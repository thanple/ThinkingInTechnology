package com.thanple.thinking.springboot.jms;

import com.thanple.thinking.springboot.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Thanple on 2017/2/24.
 */

@Component
public class Sender {

    Logger log = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;


    public void sendMail(){
        Email email = new Email("info@example.com", "Hello");
        log.info("Sending an email message:{}",email);

        //发送消息
        jmsTemplate.convertAndSend("mailbox", email);
    }
}
