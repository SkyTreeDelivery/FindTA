package com.find.Service;

import com.find.Util.Enum.EnumImp.ControlEnum.FriendRequestHandleEnum;
import com.find.Util.Exception.CustomException;
import com.find.pojo.po.*;
import com.find.pojo.dto.DtoPo.FriendRequestDTO;
import com.find.pojo.dto.DtoPo.UserDTO;
import com.find.pojo.dto.DtoPo.UserTagDTO;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    /*====================== User ==============================*/
    User register(UserDTO userDTO) throws CustomException;

    Boolean verifyUser(String username, String password);

    User login(String username, String password, String cid) throws CustomException;

    Boolean logout(String token) throws CustomException;

    Boolean updateUser(User user);

    Boolean uploadFaceImage(String faceImageBase64,Integer userId) throws Exception;

    User getUserById(Integer userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByToken(String token);

    List<User> listUsersByNickname(String nickname);

    List<UserSearchVO> listUsersByLoc(UserLocDTO userLocDTO);

    List<User> listUsersByTagId(Integer tagId);

    Boolean tokenIsExist(String token);

    Boolean userIsExist(Integer userId);

    Boolean usernameIsExist(String username);

    Boolean emailIsExist(String email);

    Boolean removeUserById(Integer userId);

    Boolean verifyToken(String token);

    /*====================== Friend（Link User to User） ==============================*/
    Boolean addFriend(Friend friend);

    List<FriendListVO> listFriendsByUserId(Integer userId) throws Exception;

    Friend getFriendById(Integer friendId);

    Friend getFriendBySendIdAndAcceptId(Integer sendUserId,Integer acceptUserId);

    Boolean removeFriend(Integer myId, Integer friendId) throws CustomException;

    Boolean friendIsExist(Integer friendRelationId);

    /*====================== Message（Link User To User） ==============================*/
    Boolean addMessage(Message message);

    Message getMessageById(Integer messageId);

    List<MessageVO> listChatMsg(MessageDTO messageDTO);

    Map<Integer, List<MessageVO>> listAllUnSignMessage(Integer userId);

    Boolean signMessages(List<Integer> ids);

    Boolean signAllMessages(Integer userId);

    Boolean removeMessageById(Integer messageId);

    Boolean messageIsExist(Integer messageId);

    /*====================== ChatPart（one to many） message ==============================*/

    Boolean addChatPart(Integer userAId, Integer userBId);

    Boolean updateChatPart(ChatPart chatPart);

    ChatPart getChatPartById(Integer chatPartId);

    ChatPart getChatPartByStrPart(String strPart);

    Boolean chatPartIsExist(Integer chatPart);

    /*====================== FriendRequestVO（Link User To User）==============================*/
    Boolean addFriendRequest(FriendRequestDTO friendRequestDTO) throws CustomException;

    FriendRequest getFriendRequestById(Integer frid);

    List<FriendRequestListVO> listFriendRequestsByUserId(Integer userId) throws Exception;

    Boolean handleFriendRequest(Integer friendRequestId, FriendRequestHandleEnum reply) throws CustomException;

    Boolean removeFriendRequestById(Integer frid);

    Boolean friendRequestIsExist(Integer friendRequestId);

    /*====================== UserTag（Link User To Tag） ==============================*/
    Boolean addUserTag(UserTagDTO userTagDTO) throws CustomException;

    UserTag getUserTagById(Integer userTagId);

    List<UserTagVO> listTagsByUserId(Integer userId) throws CustomException;

    Boolean removeUserTagById(Integer userTagId);

    Boolean userTagIsExist(Integer userTagId);
}
