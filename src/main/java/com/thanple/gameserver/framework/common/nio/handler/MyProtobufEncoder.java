package com.thanple.gameserver.framework.common.nio.handler;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.util.List;

/**
 * Created by Thanple on 2017/1/22.
 */
public class MyProtobufEncoder extends ProtobufEncoder {

    public MyProtobufEncoder(){
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
        super.encode(ctx,msg,out);
    }
}
