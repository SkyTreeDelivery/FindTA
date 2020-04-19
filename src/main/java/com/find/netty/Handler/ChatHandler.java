package com.find.netty.Handler;

import com.alibaba.fastjson.JSON;
import com.find.Service.ServiceImp.UserServiceImp;
import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.ControlEnum.ChatHandlerEnum;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.SpringUtils;
import com.find.Util.Utils.StringUtils;
import com.find.pojo.po.Message;
import com.find.pojo.dto.ChatHandlerDTO;
import com.find.pojo.vo.MessageVO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

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

        ChatHandlerDTO chatHandlerDTO = null;
        try {
            chatHandlerDTO = JSON.parseObject(content,ChatHandlerDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Integer type = chatHandlerDTO.getHandlerType();
        logger.info("type为" + type);

        if(type == ChatHandlerEnum.CREATE_CONNECTION.code){
            Integer connectUserId = chatHandlerDTO.getConnectUserId();
            userIdChannelMap.put(connectUserId,channelHandlerContext.channel());
            logger.info("{" + connectUserId + ":"
                    + channelHandlerContext.channel() + "}用户id-channel对已添加" );
        }else if(type == ChatHandlerEnum.SAVE_MESSAGE.code){
            Boolean isOnline = true;

            Message message = chatHandlerDTO.getMessage();
            if(type == null || message == null){
                throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
            }
            Integer sendUserId = message.getSendUserId();
            Integer acceptUserId = message.getAcceptUserId();
            String msgStr = message.getMessage();

            UserService userService = SpringUtils.getBean(UserServiceImp.class);
            //用户必须存在
            if(userService.userIsExist(sendUserId) == null || userService.userIsExist(acceptUserId) == null){
                throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
            }
            if(StringUtils.isBlank(msgStr)){
                throw new CustomException(CustomErrorCodeEnum.MESSAGE_IS_BLANK);
            }

            if(userIdChannelMap.get(sendUserId) == null){
                isOnline = false;
                //TODO 发送提示
            }
            Channel sendUserChannel = userIdChannelMap.get(sendUserId);
            if(clients.find(sendUserChannel.id()) == null){
                isOnline = false;
                //TODO 发送提示
            }
            // 保存消息
            message.setSignFlag(0);
            userService.addMessage(message);

            logger.info("message:" + msgStr + "已存储到数据库，由id：" + sendUserId + "发送给id：" + acceptUserId);
//            向对方返回一条指定的消息
            Channel targetChannel = userIdChannelMap.get(acceptUserId);
//            如果在线，则通过websocket连接发送消息
            if(isOnline){
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(message,messageVO);
                targetChannel.writeAndFlush(
                        new TextWebSocketFrame(messageVO.toString())
                );
            }

        }else if(type == ChatHandlerEnum.SIGN_MESSAGE.code){
            UserService userService = SpringUtils.getBean(UserServiceImp.class);
            userService.signMessage(chatHandlerDTO.getIds());
            logger.info("id为" + chatHandlerDTO.getIds() + "的message已签收");
        }else if(type == ChatHandlerEnum.HEART_TEST.code){

        }else{
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
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
