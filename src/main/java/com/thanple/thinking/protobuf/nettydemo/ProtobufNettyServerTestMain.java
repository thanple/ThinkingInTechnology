package com.thanple.thinking.protobuf.nettydemo;

import com.thanple.thinking.protobuf.PersonProtos;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


/**
 * Created by Thanple on 2017/1/19.
 */
public class ProtobufNettyServerTestMain {

    public static void main(String[] args) {
        //bossGroup : NIO selector threadPool
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup : socket data read-write worker threadPool
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(PersonProtos.Person.getDefaultInstance())) //Protobuf解码器
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())    //添加32位int表示报文的长度
                                    .addLast(new ProtobufEncoder()) //Protobuf编码器
                                    .addLast(new ProtobufServerHandler());//自定义handler
                        }
                    }).childOption(ChannelOption.TCP_NODELAY,true);
            System.out.println("begin");
            //bind到本地的18080端口
            ChannelFuture future = bootstrap.bind(18080).sync();
            //阻塞，直到channel.close
            future.channel().closeFuture().sync();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //辅助线程优雅退出
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}