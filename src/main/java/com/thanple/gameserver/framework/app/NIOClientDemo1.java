package com.thanple.gameserver.framework.app;

import com.google.protobuf.MessageLite;
import com.thanple.gameserver.framework.common.nio.protocol.*;
import com.thanple.gameserver.framework.common.provider.ProtocolLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Thanple on 2017/1/20.
 */
public class NIOClientDemo1 {


    private static class ProtobufClientHandler extends ChannelHandlerAdapter {


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //当channel就绪后，我们首先通过client发送一个数据。
            ctx.writeAndFlush(build());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            _GameServerSMsg.GameServerSMsg serverSMsg = (_GameServerSMsg.GameServerSMsg)msg;

            _SPerson1.SPerson1 person = _SPerson1.SPerson1.parseFrom(serverSMsg.getMsg());

            System.out.println(person);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();;
            ctx.close();
        }

        public MessageLite build() {

            try {
                ProtocolLoader.getInstance().init();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            _GameServerCMsg.GameServerCMsg.Builder clientMsgBuilder = _GameServerCMsg.GameServerCMsg.newBuilder();
            String clsName = _CPerson1.CPerson1.class.getSimpleName();
            int id = ProtocolLoader.getInstance().getClassProtocolmap().get(clsName);
            clientMsgBuilder.setId(id);


            _CPerson1.CPerson1.Builder personBuilder = _CPerson1.CPerson1.newBuilder();
            personBuilder.setEmail("lisi@gmail.com");
            personBuilder.setId(0);
            _CPerson1.CPerson1.PhoneNumber.Builder phone = _CPerson1.CPerson1.PhoneNumber.newBuilder();
            phone.setNumber("18610000000");

            personBuilder.setName("李四");
            personBuilder.addPhone(phone);

            clientMsgBuilder.setMsg(personBuilder.build().toByteString());

            return clientMsgBuilder.build();
        }

    }

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("initChannel");
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(_GameServerSMsg.GameServerSMsg.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new ProtobufClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 18080));
            System.out.println("begin");
            future.channel().closeFuture().sync();
            System.out.println("Closed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
