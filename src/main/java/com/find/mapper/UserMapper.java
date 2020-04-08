package com.find.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.find.pojo.*;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.FriendListVO;
import com.find.pojo.vo.MessageVO;
import com.find.pojo.vo.UserTagVO;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*====================== User ==============================*/
    Boolean doCreateUser(User user);

    Boolean updateUser(User user);

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByToken(String token);

    List<User> listUsersByNcikname(String username);

    List<User> listUserByLoc(UserLocDTO userLocDTO);

    List<User> listUsersByTagList(List<Integer> tagIdList);

    Boolean deleteUserById(Integer userId);

    /*====================== Friend（Link User to User） ==============================*/
    Boolean doCreateFriend(Friend friend);

    List<FriendListVO> listFriendsByUserId(Integer friendId);

    Friend getFriendBySendIdAndAcceptId(Integer sendUserId,Integer acceptUserId);

    Boolean deleteFriendByMyIdAndFriendId(Integer myId, Integer friendId);

    /*====================== Message（Link User To User） ==============================*/
    Boolean doCreateMessage(Message message);

    List<MessageVO> listMessages(MessageDTO messageDTO);

    Boolean signMessage(List<Integer> ids);

    Boolean deleteMessage(Integer messageId);

    /*====================== FriendRequest（Link User To User）==============================*/
    Boolean doCreateFriendRequest(FriendRequest request);

    Boolean updateFriendRequest(FriendRequest request);

    FriendRequest getFriendRequestById(Integer frId);

    FriendRequest getFriendRequestBySendIdAndAcceptId(Integer sendUserId,Integer acceptUserId);

    List<FriendListVO> listFriendRequestsByUserId(Integer userId);

    Boolean deleteFriendRequestById(Integer requestId);

    /*====================== UserTag（Link User To Tag） ==============================*/
    Boolean doCreateUserTag(UserTag userTag);

    Boolean updateUserTag(UserTag userTag);

    List<UserTagVO> listTagsByUserId(Integer userId);

    Boolean deleteUserTagById(Integer userTagId);

}
