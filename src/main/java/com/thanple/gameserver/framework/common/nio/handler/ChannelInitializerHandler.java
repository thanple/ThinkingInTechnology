package com.thanple.gameserver.framework.common.nio.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by Thanple on 2017/1/22.
 */
public class ChannelInitializerHandler extends ChannelInitializer<SocketChannel> {

    public ChannelInitializerHandler(){
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new MyProtobufDecoder()) //自定义Protobuf解码器
                .addLast(new ProtobufVarint32LengthFieldPrepender())    //添加32位int表示报文的长度
                .addLast(new MyProtobufEncoder()) //自定义Protobuf编码器
                .addLast(new ServerHandler());//自定义handler处理消息
    }
}
