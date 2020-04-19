package com.find.Controller;


import com.find.Service.TagService;
import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.ControlEnum.FriendRequestHandleEnum;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.BeanArrayUtils;
import com.find.Util.Utils.StringUtils;
import com.find.Util.Utils.ValidateUtils;
import com.find.pojo.po.*;
import com.find.pojo.dto.DtoPo.FriendRequestDTO;
import com.find.pojo.dto.DtoPo.UserDTO;
import com.find.pojo.dto.DtoPo.UserTagDTO;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user/*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @RequestMapping("register")
    public Map<String, String> register(@Valid UserDTO userDTO, BindingResult bindingResult) throws Exception {
        //1. 判断必要参数，如缺失则结束
        ValidateUtils.handlerValidateResult(bindingResult);
        String token = userService.register(userDTO);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        return tokenMap;
    }


    @RequestMapping("login")
    public Map<String, String> loginUser(String username, String password, String cid) throws CustomException {
//        1. 判断参数
        if(StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(cid)){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        String token = userService.login(username,password,cid);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        return tokenMap;
    }

    @RequestMapping("logout")
    public Boolean logOut(String token) throws CustomException {
//        这里的token已经通过了鉴权，故不需要再次验证
        return userService.logout(token);
    }

    @RequestMapping("updateUserInfo")
    public Boolean updateUserInfo(User user) throws CustomException {
        if(user.getId() == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userIsExist(user.getId())){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        user.setToken(null);
        Boolean result = userService.updateUser(user);
        return result;
    }

    @RequestMapping("queryUserById")
    public User queryUserById(Integer userId) throws CustomException {
        if(userId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userIsExist(userId)){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        return userService.getUserById(userId);
    }

    @RequestMapping("queryUsersByNickName")
    public List<UserSearchVO> queryUsersByNickName(String nickname ) throws Exception {
        if(StringUtils.isBlank(nickname)){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<User> users = userService.listUsersByNickname(nickname);
        List<UserSearchVO> userVOs = BeanArrayUtils.copyListProperties(users,UserSearchVO.class);
        return userVOs;
    }

    @RequestMapping("queryUsersByLoc")
    public List<UserSearchVO> queryUsersByLoc
            (@Valid UserLocDTO userLocDTO,BindingResult bindingResult ) throws Exception {
        ValidateUtils.handlerValidateResult(bindingResult);
        List<User> users = userService.listUsersByLoc(userLocDTO);
        List<UserSearchVO> userVOs = BeanArrayUtils.copyListProperties(users,UserSearchVO.class);
        return userVOs;
    }

    @RequestMapping("queryUsersByTags")
    public List<UserSearchVO> queryUsersByTag(Integer tagId) throws Exception {
        if(tagId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<User> users = userService.listUsersByTagId(tagId);
        List<UserSearchVO> userVOs = BeanArrayUtils.copyListProperties(users,UserSearchVO.class);
        return userVOs;
    }

    /*============================ friendrequest =======================================================*/

    @RequestMapping("requestFriend")
    public Boolean requestFriend(@Valid FriendRequestDTO requestDTO, BindingResult bindingResult) throws Exception {
//        判断参数是否合法
        ValidateUtils.handlerValidateResult(bindingResult);
        if(!userService.userIsExist(requestDTO.getSendUserId()) || !userService.userIsExist(requestDTO.getAcceptUserId())){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        Boolean result = userService.addFriendRequest(requestDTO);
        return result;
    }

    @RequestMapping("queryFriendRequestById")
    public FriendRequest queryFriendRequestById(Integer friendRequestId) throws CustomException {
        if(friendRequestId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.friendRequestIsExist(friendRequestId)){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_REQUEST_NOT_EXIST);
        }
        return userService.getFriendRequestById(friendRequestId);
    }

    @RequestMapping("queryFriendRequests")
    public List<FriendRequestListVO> queryFriendRequests(Integer userId) throws Exception {
        if(userId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userIsExist(userId)){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        List<FriendRequestListVO> friendRequestListVOList = userService.listFriendRequestsByUserId(userId);
        return friendRequestListVOList;
    }

    @RequestMapping("handleFriendRequest")
    public Boolean handleFriendRequest
            (Integer friendRequestId, FriendRequestHandleEnum reply) throws Exception {
        if(friendRequestId == null || reply == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.handleFriendRequest(friendRequestId, reply);
        return result;
    }

    /*============================ friend =======================================================*/

    @RequestMapping("queryFriendById")
    public Friend queryFriendById(Integer friendId) throws CustomException {
        if(friendId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.friendIsExist(friendId)){
            throw new CustomException(CustomErrorCodeEnum.NO_FRINEND_RELATION);
        }
        return userService.getFriendById(friendId);
    }


    @RequestMapping("queryFriends")
    public List<FriendListVO> queryFriends(Integer userId) throws Exception {
        if(userId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<FriendListVO> friendList = userService.listFriendsByUserId(userId);
        return friendList;
    }

    @RequestMapping("deleteFriend")
    public Boolean deleteFriend
            (Integer myId, Integer friendId) throws Exception {
        if(myId == null || friendId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userIsExist(myId)|| !userService.userIsExist(friendId)){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        Boolean result = userService.removeFriend(myId,friendId);
        return result;
    }


    /*============================ message =======================================================*/
    @RequestMapping("queryMessageById")
    public Message queryMessageById(Integer messageId) throws CustomException {
        if(messageId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.messageIsExist(messageId)){
            throw new CustomException(CustomErrorCodeEnum.NO_MESSAGE);
        }
        return userService.getMessageById(messageId);
    }

    @RequestMapping("listChatMsg")
    public List<MessageVO> listChatMsg(@Valid MessageDTO messageDTO,BindingResult bindingResult) throws CustomException {
        ValidateUtils.handlerValidateResult(bindingResult);
        if(!userService.userIsExist(messageDTO.getSendUserId())||!userService.userIsExist(messageDTO.getAcceptUserId())){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        List<MessageVO> messageVOS = userService.listChatMsg(messageDTO);
        return messageVOS;
    }

    @RequestMapping("signAlldMessage")
    public Boolean signAllUnSignedMessage(Integer userId) throws CustomException {
        if(userId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userIsExist(userId)){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        return userService.signAllMessages(userId);
    }

    /*============================ tag =======================================================*/

    @RequestMapping("addUserTag")
    public Boolean addUserTag(@Valid UserTagDTO userTagDTO, BindingResult bindingResult) throws CustomException {
        ValidateUtils.handlerValidateResult(bindingResult);
        if(!userService.userIsExist(userTagDTO.getUserId()) || !tagService.tagIsExist(userTagDTO.getTagId())){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.addUserTag(userTagDTO);
        return result;
    }

    @RequestMapping("queryUserTagById")
    public UserTag queryUserTagById(Integer userTagId) throws CustomException {
        if(userTagId == null ){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        if(!userService.userTagIsExist(userTagId)){
            throw new CustomException(CustomErrorCodeEnum.NO_USER_TAG);
        }
        return userService.getUserTagById(userTagId);
    }

    @RequestMapping("queryTagsByUserid")
    public List<UserTagVO> queryTagsByUserid(Integer userId) throws CustomException {
        if(userId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<UserTagVO> tags = userService.listTagsByUserId(userId);
        return tags;
    }

    @RequestMapping("deleteUserTag")
    public Boolean deleteUserTag(Integer userTagId) throws CustomException {
        if(userTagId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.removeUserTagById(userTagId);
        return result;
    }
}
