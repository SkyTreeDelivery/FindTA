package com.find.Service;

import com.find.Util.Exception.CustomException;
import com.find.pojo.*;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.FriendListVO;
import com.find.pojo.vo.FriendRequestListVO;
import com.find.pojo.vo.MessageVO;
import com.find.pojo.vo.UserTagVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /*====================== User ==============================*/
    String register(User user) throws CustomException;

    Boolean verifyUser(String username, String password);

    String login(User user) throws CustomException;

    Boolean updateUser(User user);

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByToken(String token);

    List<User> listUsersByNickname(String nickname);

    List<User> listUsersByLoc(UserLocDTO userLocDTO);

    List<User> listUsersByTagList(List<Integer> tagIdList);

    Boolean tokenIsExist(String token);

    Boolean userIsExist(Integer userId);

    Boolean usernameIsExist(String username);

    Boolean emailIsExist(String email);

    Boolean removeUserById(Integer userId);

    Boolean verifyToken(String token);

    /*====================== Friend（Link User to User） ==============================*/
    Boolean addFriend(Friend friend);

    List<FriendListVO> listFriendsByUserId(Integer userId);

    Friend getFriendBySendIdAndAcceptId(Integer sendUserId,Integer acceptUserId);

    Boolean deleteFriend(Integer myId, Integer friendId) throws CustomException;

    /*====================== Message（Link User To User） ==============================*/
    Boolean addMessage(Message message);

    List<MessageVO> listChatMsg(MessageDTO messageDTO);

    Boolean signMessage(List<Integer> ids);

    Boolean removeMessageById(Integer messageId);

    /*====================== FriendRequestVO（Link User To User）==============================*/
    Boolean addFriendRequest(FriendRequest friendRequest) throws CustomException;

    FriendRequest getFriendRequestById(Integer frid);

    List<FriendRequestListVO> listFriendRequestsByUserId(Integer userId);

    Boolean handleFriendRequest(Integer friendRequestId, Boolean reply) throws CustomException;

    Boolean removeFriendRequestById(Integer frid);

    /*====================== UserTag（Link User To Tag） ==============================*/
    Boolean addUserTag(UserTag userTag) throws CustomException;

    List<UserTagVO> listTagsByUserId(Integer userId) throws CustomException;

    Boolean removeUserTagById(Integer userTagId);
}
