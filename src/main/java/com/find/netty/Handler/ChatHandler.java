package com.find.netty.Handler;

import com.alibaba.fastjson.JSONObject;
import com.find.Service.ServiceImp.UserServiceImp;
import com.find.Service.UserService;
import com.find.Util.Enum.ChatHandlerEnum;
import com.find.Util.Enum.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.GeoJsonUtils;
import com.find.Util.Utils.SpringUtils;
import com.find.Util.Utils.StringUtils;
import com.find.pojo.Message;
import com.find.pojo.dto.ChatHandlerDTO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    管理所有的客户端channel
    private static ChannelGroup clients =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static HashMap<Integer, Channel> userIdChannelMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
//        获取从客户端接收数据
        String content = textWebSocketFrame.text();
        logger.debug("接收的数据  ：  " + content);

        ChatHandlerDTO chatHandlerDTO = (ChatHandlerDTO) JSONObject.parse(textWebSocketFrame.text());
        Integer type = chatHandlerDTO.getHandlerType();
        Message message = chatHandlerDTO.getMessage();

        if(type == ChatHandlerEnum.CREATE_CONNECTION.code){
            Integer sendUserId = message.getSendUserId();
            userIdChannelMap.put(sendUserId,channelHandlerContext.channel());
        }else if(type == ChatHandlerEnum.SAVE_MESSAGE.code){
            Integer sendUserId = message.getSendUserId();
            Integer acceptUserId = message.getAcceptUserId();
            String msgStr = message.getMessage();

            if(userIdChannelMap.get(sendUserId) == null){
                //TODO 发送提示
            }
            Channel sendUserChannel = userIdChannelMap.get(sendUserId);
            if(clients.find(sendUserChannel.id()) == null){
                //TODO 发送提示
            }
            // 保存消息
            UserService userService = SpringUtils.getBean(UserServiceImp.class);
            //用户必须存在
            if(userService.userIsExist(sendUserId) == null || userService.userIsExist(acceptUserId) == null){
                throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
            }
            //传入的地址信息必须正确
            if(message.getSendUserLoc() != null){
                if(!GeoJsonUtils.isPoint(message.getSendUserLoc())){
                    throw new CustomException(CustomErrorCodeEnum.GEOJSON_CANT_PARSE);
                }
            }
            //传入的地址信息必须正确
            if(message.getAcceptUserLoc() != null){
                if(!GeoJsonUtils.isPoint(message.getAcceptUserLoc())){
                    throw new CustomException(CustomErrorCodeEnum.GEOJSON_CANT_PARSE);
                }
            }
            if(StringUtils.isBlank(msgStr)){
                throw new CustomException(CustomErrorCodeEnum.MESSAGE_IS_BLANK);
            }
            message.setSignFlag(0);
            userService.addMessage(message);

        }else if(type == ChatHandlerEnum.SIGN_MESSAGE.code){
            UserService userService = SpringUtils.getBean(UserServiceImp.class);
            userService.signMessage(chatHandlerDTO.getIds());
        }else if(type == ChatHandlerEnum.HEART_TEST.code){

        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        将channel添加到管理
        logger.debug("channel" + ctx.channel().id().asLongText() + "被添加");
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel" + ctx.channel().id().asLongText() + "被移除");
        logger.debug(ctx.channel().id().asLongText());
        logger.debug(ctx.channel().id().asShortText());
        clients.remove(ctx.channel());
    }
}
