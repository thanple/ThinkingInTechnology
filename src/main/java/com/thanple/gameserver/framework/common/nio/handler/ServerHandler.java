package com.thanple.gameserver.framework.common.nio.handler;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.thanple.gameserver.framework.app.msg.Person;
import com.thanple.gameserver.framework.common.berkeleydb.Procedure;
import com.thanple.gameserver.framework.common.berkeleydb.entity.User;
import com.thanple.gameserver.framework.common.berkeleydb.table.UserTable;
import com.thanple.gameserver.framework.common.nio.protocol.Protocol;
import com.thanple.gameserver.framework.common.nio.protocol._GameServerCMsg;
import com.thanple.gameserver.framework.common.nio.protocol._Person;
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
        return _Person.Person.class;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //先使用万能消息模板接收消息
        _GameServerCMsg.GameServerCMsg clientMsg = (_GameServerCMsg.GameServerCMsg)msg;
        int id = clientMsg.getId();
        ByteString data = clientMsg.getMsg();

        //反射万能消息模板的data得到协议消息对象(protobuf生成的)
        Class<?> protocolCls = this.getClassById(id);
        Method parseProtoFromByteString = protocolCls.getDeclaredMethod("parseFrom",ByteString.class);
        com.google.protobuf.MessageLite messageLite = (com.google.protobuf.MessageLite)parseProtoFromByteString.invoke(null,data);

        //反射逻辑业务类（自定义生成）
        Class<Protocol> logicProtocolCls = (Class<Protocol>)Class.forName(
                "com.thanple.gameserver.framework.app.msg."+messageLite.getClass().getSimpleName());
        Protocol obj = logicProtocolCls.getConstructor(protocolCls).newInstance(messageLite);


        //反射得到的协议分发业务方法
        obj.process(ctx);

//        ChannelFuture future = ctx.writeAndFlush(build());
        //发送数据之后，我们手动关闭channel，这个关闭是异步的，当数据发送完毕后执行。
//        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 构建一个Protobuf实例，测试
     * @return

    public MessageLite build() {

        _Person.Person.Builder personBuilder = _Person.Person.newBuilder();
        personBuilder.setEmail("zhangsan@gmail.com");
        personBuilder.setId(1000);
        _Person.Person.PhoneNumber.Builder phone = _Person.Person.PhoneNumber.newBuilder();
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
    }*/

}