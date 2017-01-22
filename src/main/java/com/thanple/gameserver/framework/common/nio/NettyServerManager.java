package com.thanple.gameserver.framework.common.nio;

import com.thanple.gameserver.framework.common.nio.handler.ChannelInitializerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Thanple on 2017/1/20.
 */
public class NettyServerManager {
    private static NettyServerManager nettyServer = new NettyServerManager();
    private NettyServerManager(){}
    public static NettyServerManager getInstance(){
        return  nettyServer;
    }

    public void init(){
        //bossGroup : NIO selector threadPool
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup : socket data read-write worker threadPool
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializerHandler())  //自定义ChannelHandler，Handler的处理入口类
                    .childOption(ChannelOption.TCP_NODELAY,true);
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
