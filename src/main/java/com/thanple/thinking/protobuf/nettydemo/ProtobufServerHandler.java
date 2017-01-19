package com.thanple.thinking.protobuf.nettydemo;

import com.google.protobuf.MessageLite;
import com.thanple.thinking.protobuf.PersonProtos;
import io.netty.channel.*;

/**
 * Created by Thanple on 2017/1/19.
 */
public class ProtobufServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PersonProtos.Person person = (PersonProtos.Person)msg;
        //经过pipeline的各个decoder，到此Person类型已经可以断定
        System.out.println(person.getEmail());
        ChannelFuture future = ctx.writeAndFlush(build());
        //发送数据之后，我们手动关闭channel，这个关闭是异步的，当数据发送完毕后执行。
        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 构建一个Protobuf实例，测试
     * @return
     */
    public MessageLite build() {
        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        personBuilder.setEmail("zhangsan@gmail.com");
        personBuilder.setId(1000);
        PersonProtos.Person.PhoneNumber.Builder phone = PersonProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");

        personBuilder.setName("张三");
        personBuilder.addPhone(phone);

        return personBuilder.build();
    }

}