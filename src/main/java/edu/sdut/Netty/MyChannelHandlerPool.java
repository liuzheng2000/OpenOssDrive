package edu.sdut.Netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * MyChannelHandlerPool
 * 通道组池，管理所有websocket连接
 * @author qingyun
 * @version 1.0
 * @date 2021/11/19 15:55
 */
public class MyChannelHandlerPool {
    public MyChannelHandlerPool(){

    }

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
