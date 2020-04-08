package com.find.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WSServer {

    //        生成主从事件管理器
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;
     private WSServer(){
         bossGroup  = new NioEventLoopGroup();
         workerGroup = new NioEventLoopGroup();
         serverBootstrap = new ServerBootstrap();
         serverBootstrap.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)   //指定nio传输channel
                 .childHandler(new WebSocketInitializer());  //添加启动器
     }

     public void start(){
         channelFuture = serverBootstrap.bind(8088); //同步绑定端口
     }

     public static WSServer getInstance(){
         return new WSServer();
     }
}
