package com.thanple.gameserver.framework.common.nio.handler;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.thanple.gameserver.framework.common.berkeleydb.Procedure;
import com.thanple.gameserver.framework.common.berkeleydb.entity.User;
import com.thanple.gameserver.framework.common.berkeleydb.table.UserTable;
import com.thanple.gameserver.framework.common.nio.protocol.GameServerCMsg;
import com.thanple.gameserver.framework.common.nio.protocol.PersonProtos;
import com.thanple.gameserver.framework.common.provider.TableLoader;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * Created by Thanple on 2017/1/20.
 */
public class ServerHandler extends ChannelHandlerAdapter {


    private Class<?> getClassById(int classId){
        return PersonProtos.Person.class;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        GameServerCMsg.ClientMsg clientMsg = (GameServerCMsg.ClientMsg)msg;

        int id = clientMsg.getId();
        ByteString data = clientMsg.getMsg();

        Class<?> protocolCls = this.getClassById(id);
        Method parseProtocloFromByteString = protocolCls.getDeclaredMethod("parseFrom",ByteString.class);
        Object obj = parseProtocloFromByteString.invoke(null,data);

        PersonProtos.Person person = (PersonProtos.Person)obj;
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


        personBuilder.addPhone(phone);

        new Procedure() {
            @Override
            protected boolean process() {

                UserTable user = TableLoader.getTableInstance(UserTable.class);
                User userEntity1 = user.select(1L);


                personBuilder.setName(userEntity1.getName());

                return true;
            }
        }.submit();




        return personBuilder.build();
    }

}