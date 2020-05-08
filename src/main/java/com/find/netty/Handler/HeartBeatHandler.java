package com.find.netty.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        Channel channel = ctx.channel();
        if(event.state() == IdleState.READER_IDLE){
            logger.debug("channel-" + channel.id().asShortText() +"进入读空闲...");
        }else if(event.state() == IdleState.WRITER_IDLE){
            logger.debug("channel-" + channel.id().asShortText() +"进入写空闲...");
        }else if(event.state() == IdleState.ALL_IDLE){
            logger.debug("channel-" + channel.id().asShortText() +"进入读写空闲...");
            channel.close();
            logger.debug("channel-" + channel.id().asShortText() + "关闭");
        }
    }
}
