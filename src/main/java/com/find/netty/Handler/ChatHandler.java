package com.find.netty.Handler;

import com.alibaba.fastjson.JSON;
import com.find.Service.ServiceImp.UserServiceImp;
import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.ControlEnum.ChatHandlerEnum;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.HttpResultBuiler;
import com.find.Util.Utils.BeanArrayUtils;
import com.find.Util.Utils.SpringUtils;
import com.find.Util.Utils.StringUtils;
import com.find.pojo.dto.ChatHandlerDTO;
import com.find.pojo.dto.DtoPo.MessageAcceptDTO;
import com.find.pojo.po.Message;
import com.find.pojo.po.User;
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
import org.springframework.beans.BeansException;

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
        try {
            Object data = null;
            String content = textWebSocketFrame.text();
            logger.debug("接收的数据  ：  " + content);

            ChatHandlerDTO chatHandlerDTO = null;
            try {
                chatHandlerDTO = JSON.parseObject(content, ChatHandlerDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR,"chatHandlerDTO解析异常");
            }
            // 获得userService
            UserService userService = SpringUtils.getBean(UserServiceImp.class);

            // 验证token，如不存在则退出，如存在则使用该token对应的id
            String token = chatHandlerDTO.getToken();
            Boolean isExist = userService.tokenIsExist(token);
            if (!isExist) {
                throw new CustomException(CustomErrorCodeEnum.PARAM_INVALID_TOKEN);
            }
            User user = userService.getUserByToken(token);
            Integer connectUserId = user.getId();

            // 获取操作类型
            Integer type = chatHandlerDTO.getHandleType();
            logger.info("type为" + type);

            // 执行处理
            if (type == ChatHandlerEnum.CREATE_CONNECTION.code) {
                // 添加链接-用户id对
                userIdChannelMap.put(connectUserId, channelHandlerContext.channel());
                logger.info("{" + connectUserId + ":"
                        + channelHandlerContext.channel() + "}用户id-channel对已添加");
                data = true;
            } else if (type == ChatHandlerEnum.SAVE_MESSAGE.code) {

                // 首先验证信息的正确性，如不通过则退出，如通过，保存消息，如果消息接收的对象在线，则发送消息
                boolean isOnline = true;
                // 提取message中的数据进行验证
                MessageAcceptDTO message = chatHandlerDTO.getMessage();
                if (message == null) {
                    throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR,"message为空");
                }
                Integer sendUserId = user.getId();
                Integer acceptUserId = message.getAcceptUserId();
                String msgStr = message.getMessage();

                Message saveMessage = BeanArrayUtils.copyProperties(message, Message.class);

                // 用户必须存在
                if (!userService.userIsExist(sendUserId) || !userService.userIsExist(acceptUserId)) {
                    throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
                }
                if (StringUtils.isBlank(msgStr)) {
                    throw new CustomException(CustomErrorCodeEnum.MESSAGE_IS_BLANK);
                }

                // 保存消息
                saveMessage.setSendUserId(user.getId());
                saveMessage.setSignFlag(0);
                userService.addMessage(saveMessage);

                // 查询新插入的一条message
                Message reMessage = userService.getMessageById(saveMessage.getId());
                data = BeanArrayUtils.copyProperties(reMessage, MessageVO.class);

                // 如果用户不在线则发送离线提示
                if (userIdChannelMap.get(acceptUserId) == null) {
                    isOnline = false;
                    //TODO 发送提示
                }else{
                    Channel sendUserChannel = userIdChannelMap.get(acceptUserId);
                    if (clients.find(sendUserChannel.id()) == null) {
                        isOnline = false;
                        //TODO 发送提示
                    }
                }

                logger.info("message:" + msgStr + "已存储到数据库，由id：" + sendUserId + "发送给id：" + acceptUserId);
               // 向对方返回一条指定的消息
                Channel targetChannel = userIdChannelMap.get(acceptUserId);
               // 如果在线，则通过websocket连接发送消息
                if (isOnline) {
                    // messageVO包含消息Id，发送者Id，发送时间，发送内容
                    targetChannel.writeAndFlush(
                            new TextWebSocketFrame(HttpResultBuiler.ok(data).toString())
                    );
                }
            } else if (type == ChatHandlerEnum.SIGN_MESSAGE.code) {
                userService.signMessages(chatHandlerDTO.getIds());
                logger.info("id为" + chatHandlerDTO.getIds() + "的message已签收");
                data = true;
            } else if (type == ChatHandlerEnum.HEART_TEST.code) {
                logger.debug("channel-" + channelHandlerContext.channel().id().asShortText() + "进行了心跳检测");
                data = true;
            } else {
                throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR, "操作代码越界（只接受handleType=0,1,2,3");
            }

            //向操作发起者返回执行结果
            channelHandlerContext.channel().writeAndFlush(
                    new TextWebSocketFrame(HttpResultBuiler.ok(data).toString())
            );

        } catch (CustomException e) {
            logger.info(e.getMessage());
            // 向消息发送者传递错误信息
            channelHandlerContext.channel().writeAndFlush(
                    new TextWebSocketFrame(HttpResultBuiler.exception(e).toString())
            );
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       // 将channel添加到管理
        logger.debug("channel" + ctx.channel().id().asLongText() + "被添加");
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel-" + ctx.channel().id().asLongText() + "被移除");
        clients.remove(ctx.channel());
    }
}
