package com.thanple.gameserver.framework.app.msg;

import com.thanple.gameserver.framework.common.nio.protocol._Person;

/**
 * auto created by CreateProtocolUtil
 * */

public class Person extends 
       com.thanple.gameserver.framework.common.nio.protocol.Protocol
               <com.thanple.gameserver.framework.common.nio.protocol._Person.Person>{

    public Person(
           com.thanple.gameserver.framework.common.nio.protocol._Person.Person msg){
        super(msg);
    }

    @Override
    public void process(io.netty.channel.ChannelHandlerContext ctx) {
        System.out.println(protocolBean.getName());
        _Person.Person.Builder builder = protocolBean.toBuilder();
        builder.setEmail("2222@qq.com");

        send(ctx,builder.build());
    }
}