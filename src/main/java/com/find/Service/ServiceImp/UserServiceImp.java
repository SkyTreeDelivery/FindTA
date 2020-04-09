package com.find.Service.ServiceImp;

import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.MD5Util;
import com.find.mapper.TagMapper;
import com.find.mapper.UserMapper;
import com.find.pojo.*;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.FriendListVO;
import com.find.pojo.vo.FriendRequestListVO;
import com.find.pojo.vo.MessageVO;
import com.find.pojo.vo.UserTagVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    TagMapper tagMapper;

    @Override
    public String register(User user) throws CustomException {
        //2. 如果username，email已被注册，则提示已占用
        if(usernameIsExist(user.getUsername())
                ||emailIsExist(user.getEmail())){
            throw new CustomException(CustomErrorCodeEnum.USER_HAS_EXISTED);
        }
        //3. 注册用户，注册完成后自动登录，返回token
        String token = getUniToken();
        user.setToken(token);
        Boolean result = userMapper.doCreateUser(user);
        if(result){
            return token;
        }
        return null;
    }

    private String getUniToken(){
        while (true){
            String token = UUID.randomUUID().toString();
            Boolean result = tokenIsExist(token);
            if(!result){
                return token;
            }
        }
    }

    @Override
    public Boolean verifyUser(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword() != null
                && user.getPassword().equals(MD5Util.getMD5Str(password));
    }

    @Override
    public String login(User user) throws CustomException {
//        2. 验证用户
        Boolean result = verifyUser(user.getUsername(), user.getPassword());
        if(!result){
            throw new CustomException(CustomErrorCodeEnum.USER_LOGIN_ERROR);
        }
//        3. 生成新的用户token
        String token = getUniToken();
        User userinfo = getUserByUsername(user.getUsername());
        User updateUser = new User();
        updateUser.setId(userinfo.getId());
        updateUser.setToken(token);
        Boolean r2 = updateUser(updateUser);
        if(r2){
            return token;
        }
        return null;
    }

    @Override
    public Boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }

    @Override
    public List<User> listUsersByNickname(String nickname) {
        return userMapper.listUsersByNcikname(nickname);
    }

    @Override
    public List<User> listUsersByLoc(UserLocDTO userLocDTO) {
        return userMapper.listUserByLoc(userLocDTO);
    }

    @Override
    public List<User> listUsersByTagList(List<Integer> tagIdList) {
        return userMapper.listUsersByTagList(tagIdList);
    }

    @Override
    public Boolean tokenIsExist(String token) {
        User user = userMapper.getUserByToken(token);
        return user != null;
    }

    @Override
    public Boolean userIsExist(Integer userId) {
        User user = userMapper.getUserById(userId);
        return user != null;
    }

    @Override
    public Boolean usernameIsExist(String username) {
        User user = userMapper.getUserByUsername(username);
        return user != null;
    }

    @Override
    public Boolean emailIsExist(String email) {
        User user = userMapper.getUserByEmail(email);
        return user != null;
    }

    @Override
    public Boolean removeUserById(Integer userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public Boolean verifyToken(String token) {
        User user = getUserByToken(token);
        return user != null;
    }

    @Override
    public Boolean addFriend(Friend friend) {
        return userMapper.doCreateFriend(friend);
    }

    @Override
    public List<FriendListVO> listFriendsByUserId(Integer userId) {
        return null;
    }

    @Override
    public Friend getFriendBySendIdAndAcceptId(Integer sendUserId, Integer acceptUserId) {
        return userMapper.getFriendBySendIdAndAcceptId(sendUserId,acceptUserId);
    }

    @Override
    public Boolean deleteFriend(Integer myId, Integer friendId) throws CustomException {
        if(getUserById(myId) == null || getUserById(friendId) == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
//        删除自己的好友
        Boolean r1 = userMapper.deleteFriendByMyIdAndFriendId(myId, friendId);
//        删除对方的好友并通知对方
        Boolean r2 = userMapper.deleteFriendByMyIdAndFriendId(friendId, myId);
        return r1 && r2;
    }

    @Override
    public Boolean addMessage(Message message) {
        return userMapper.doCreateMessage(message);
    }

    @Override
    public List<MessageVO> listChatMsg(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public Boolean signMessage(List<Integer> ids) {
        return userMapper.signMessage(ids);
    }

    @Override
    public Boolean removeMessageById(Integer messageId) {
        return userMapper.deleteMessage(messageId);
    }

    @Override
    public Boolean addFriendRequest(FriendRequest request) throws CustomException {
        //        判断是否添加的是自己
        if(request.getSendUserId().equals(request.getAcceptUserId())){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_CANT_ADD_SELF);
        }
//        判断是否已经添加过好友
        if(getFriendBySendIdAndAcceptId(request.getSendUserId(),request.getAcceptUserId()) != null){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_IS_EXIST);
        }
//        是否已经发送过请求,如已经发送过请求，则更新请求message
        FriendRequest friendRequest = userMapper.getFriendRequestBySendIdAndAcceptId
                (request.getSendUserId(), request.getAcceptUserId());
        if(friendRequest != null){
            userMapper.updateFriendRequest(request);
        }
//        添加好友
        return userMapper.doCreateFriendRequest(request);
    }

    @Override
    public FriendRequest getFriendRequestById(Integer frid) {
        return userMapper.getFriendRequestById(frid);
    }

    @Override
    public List<FriendRequestListVO> listFriendRequestsByUserId(Integer userId) {
        return null;
    }

    @Override
    public Boolean handleFriendRequest(Integer friendRequestId, Boolean reply) throws CustomException {
        FriendRequest friendRequest = getFriendRequestById(friendRequestId);
        if(friendRequest == null){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_REQUEST_NOT_EXIST);
        }
//        //如果存在，则说明此请求还未被处理，处理请求
//        Boolean r1 = userIsExist(friendRequest.getSendUserId());
//        Boolean r2 = userIsExist(friendRequest.getAcceptUserId());
//        if(!r1 || !r2){
//            removeFriendRequestById(friendRequestId);
//            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
//        }
        //接收请求，删除好友请求，添加好友关系
        if(reply){
            removeFriendRequestById(friendRequestId);
            //添加发送方的好友
            Friend friend = new Friend();
            friend.setMyId(friendRequest.getSendUserId());
            friend.setFriendId(friendRequest.getAcceptUserId());
            addFriend(friend);

            //添加接收方好友
            Friend friendReverse = new Friend();
            friendReverse.setMyId(friendRequest.getAcceptUserId());
            friendReverse.setFriendId(friendRequest.getSendUserId());
            addFriend(friend);
            return true;
        }else{
            //拒绝请求
            return removeFriendRequestById(friendRequestId);
        }
    }

    @Override
    public Boolean removeFriendRequestById(Integer frid) {
        return userMapper.deleteFriendRequestById(frid);
    }

    @Override
    public Boolean addUserTag(UserTag userTag) throws CustomException {
        if(getUserById(userTag.getUserId()) == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        return userMapper.doCreateUserTag(userTag);
    }

    @Override
    public List<UserTagVO> listTagsByUserId(Integer userId) throws CustomException {
        if(getUserById(userId) == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        List<UserTagVO> tags = userMapper.listTagsByUserId(userId);
        return tags;
    }

    @Override
    public Boolean removeUserTagById(Integer userTagId) {
        return userMapper.deleteUserTagById(userTagId);
    }
}
