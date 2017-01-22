package com.thanple.gameserver.framework.app.msg;

/**
 * auto created by CreateProtocolUtil
 * */

public class GameServerCMsg extends 
       com.thanple.gameserver.framework.common.nio.protocol.Protocol
               <com.thanple.gameserver.framework.common.nio.protocol._GameServerCMsg.GameServerCMsg>{

    public GameServerCMsg(
           com.thanple.gameserver.framework.common.nio.protocol._GameServerCMsg.GameServerCMsg msg){
        super(msg);
    }

    @Override
    public void process(io.netty.channel.ChannelHandlerContext ctx) {

    }
}