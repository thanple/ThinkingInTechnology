package com.thanple.gameserver.framework.app.person1.msg;

import com.thanple.gameserver.framework.common.nio.protocol._SPerson1;

/**
 * auto created by CreateProtocolUtil
 * */

public class CPerson1 extends
       com.thanple.gameserver.framework.common.nio.protocol.Protocol
               <com.thanple.gameserver.framework.common.nio.protocol._CPerson1.CPerson1>{

    public CPerson1(
           com.thanple.gameserver.framework.common.nio.protocol._CPerson1.CPerson1 msg){
        super(msg);
    }

    @Override
    public void process(io.netty.channel.ChannelHandlerContext ctx) {
        System.out.println(protocolBean);

        _SPerson1.SPerson1.Builder sPerson1 = _SPerson1.SPerson1.newBuilder();
        sPerson1.setId(1);
        sPerson1.setName("Thanple");
        sPerson1.setEmail("asdasdasd@qq.com");

        send(ctx,sPerson1.build());
    }
}