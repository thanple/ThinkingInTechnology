package com.thanple.thinking.nio.onlinechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thanple on 2017/1/18.
 *
 * NIO Server端步骤:
 * Selector.open()->ServerSocketChannel.open()
 * ->serverChannel.register(selector)事件得到SelectionKey
 * ->轮训调度selector得到的SelectionKey处理不同的事件(Accept,Read,Connection等)
 *
 * SelectionKey是什么？ServerSocketChannel注册事件后返回SelectionKey，可以注册Accept,Read,Connection等
 * SelectionKey中可以获取SocketChannel，即持有客户端的Socket实例的同道
 * 每个SelectionKey都可以attach一个Handle接口，然后自定义处理
 */
public class OnlineChatServer {

    public static final int PORT = 8080;

    public static final String  USER_CONTENT_SPILIT = "@";

    //通道管理器
    private Selector selector;

    //所有User
    private Map<String,SocketChannel> users = new HashMap<>();  //名字映射SocketChannel
    private Map<SocketChannel,String> usersChannel = new HashMap<>();   //SocketChannel映射名字

    public void init() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        this.selector = Selector.open();
        SelectionKey sk = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        //selectionKey.attach(new Handle...);   //可以在这里attach

        System.out.println("Server is started at "+PORT);

        while (true){
            int readChannels = this.selector.select();
            if(readChannels == 0) continue;
            Set<SelectionKey> selectedKeys = this.selector.selectedKeys();   //获取通道的所有集合
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                keyIterator.remove();
                this.dealWithSelectionKey(serverChannel,selectionKey);  //处理通道
            }
        }
    }

    private void dealWithSelectionKey(ServerSocketChannel serverChannel, SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){
            this.dealWithAccept(serverChannel,selectionKey);
        }else  if(selectionKey.isReadable()){
            this.dealWithRead(selectionKey);
        }
    }

    //处理连接响应
    private void dealWithAccept(ServerSocketChannel serverChannel,SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        System.out.println("Server is listening from client :" + socketChannel.getRemoteAddress());
        socketChannel.write(Charset.forName("UTF-8").encode("Please input your name:"));
    }

    //处理读取消息响应
    private void dealWithRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        StringBuilder content = new StringBuilder();
        try{
            while(socketChannel.read(buff) > 0){
                buff.flip();
                content.append(Charset.forName("UTF-8").decode(buff));
            }
            System.out.println("Server is listening from client " + socketChannel.getRemoteAddress() + " data rev is: " + content);
            selectionKey.interestOps(SelectionKey.OP_READ);//将此对应的channel设置为准备下一次接收数据
        }catch (IOException io){
            selectionKey.cancel();
            if(selectionKey.channel() != null){
                selectionKey.channel().close();
            }
        }

        //注册或者发消息
        if(content.length() > 0){
            String[] arrayContent = content.toString().split(USER_CONTENT_SPILIT);
            if(arrayContent != null && arrayContent.length ==1) {
                String name = arrayContent[0];
                if(users.keySet().contains(name)) {
                    socketChannel.write(Charset.forName("UTF-8").encode("User "+name + " is already exist!"));
                } else {
                    users.put(name,socketChannel);
                    usersChannel.put(socketChannel,name);
                    socketChannel.write(Charset.forName("UTF-8").encode("Register successfully!"));
                }
            }
            else if(arrayContent != null && arrayContent.length >1){    //注册完了，发送消息
                String writeTo = arrayContent[0];
                String message = content.substring(writeTo.length()+USER_CONTENT_SPILIT.length());


                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.sendToAnotherSocketChannel(socketChannel,writeTo,message);
            }
        }

    }


    //发送给另一个SocketChannel(发给另外一个人)
    private void sendToAnotherSocketChannel(SocketChannel thisSocketChanel ,String name,String message) throws IOException {
        SocketChannel anotherSocketChanner = users.get(name);
        if(null == anotherSocketChanner)    {
            thisSocketChanel.write(Charset.forName("UTF-8").encode("Person "+name+" is not exited!"));
            return;
        }
        if(anotherSocketChanner == thisSocketChanel){
            thisSocketChanel.write(Charset.forName("UTF-8").encode("You cannot send to your self!"));
            return;
        }
        anotherSocketChanner.write(Charset.forName("UTF-8").encode(usersChannel.get(thisSocketChanel)+" said: "+message));
    }


    public static void main(String[] args) throws IOException {
        new OnlineChatServer().init();
    }

}
