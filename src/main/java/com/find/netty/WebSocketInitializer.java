package com.find.netty;

import com.find.netty.Handler.ChatHandler;
import com.find.netty.Handler.HeartBeatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        添加http支持
        socketChannel.pipeline()
                .addLast("serverCodec", new HttpServerCodec())   // server解码编码
                .addLast("chunkedWriteHandler",new ChunkedWriteHandler())  // 添加大数据量处理
                .addLast("aggregator",new HttpObjectAggregator(1024*64));  // 将请求整合一个进行处理

        socketChannel.pipeline()
                .addLast(new IdleStateHandler(150, 225, 300)) // 添加idle时间出发器
                .addLast(new HeartBeatHandler()); // 添加心跳处理器（idle事件处理器）

        socketChannel.pipeline()
                .addLast("protocolHandler",new WebSocketServerProtocolHandler("/ws"))   //自定义协议
                .addLast("chatHandler", new ChatHandler());   //chat处理器
    }
}
