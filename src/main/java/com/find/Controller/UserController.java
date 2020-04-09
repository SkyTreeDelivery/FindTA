package com.find.Controller;


import com.find.Service.UserService;
import com.find.Util.Utils.BeanArrayUtils;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.GeoJsonUtils;
import com.find.Util.Utils.StringUtils;
import com.find.pojo.FriendRequest;
import com.find.pojo.User;
import com.find.pojo.UserTag;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.vo.FriendListVO;
import com.find.pojo.vo.FriendRequestListVO;
import com.find.pojo.vo.UserTagVO;
import com.find.pojo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user/*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("register")
    public String register(@Valid User user, BindingResult bindingResult) throws Exception {
        //1. 判断必要参数，如缺失则结束
       if(bindingResult.hasErrors()){
           throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
       }
        String token = userService.register(user);
        return token;
    }



    @RequestMapping("login")
    public String loginUser(User user) throws CustomException {
//        1. 判断参数
        if(StringUtils.isBlank(user.getUsername())|| StringUtils.isBlank(user.getPassword())){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        return userService.login(user);
    }

    @RequestMapping("updateUserInfo")
    public Boolean updateUserInfo(User user){
        Boolean result = userService.updateUser(user);
        return result;
    }

    @RequestMapping("queryUsersByNickName")
    public List<UserVO> queryUsersByNickName(String nickname ) throws Exception {
        if(StringUtils.isBlank(nickname)){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<User> users = userService.listUsersByNickname(nickname);
        List<UserVO> userVOs = BeanArrayUtils.copyListProperties(users,UserVO.class);
        System.out.println(userVOs.toString());
        return userVOs;
    }

    @RequestMapping("queryUsersByLoc")
    public List<UserVO> queryUsersByLoc
            (@Valid UserLocDTO userLocDTO,BindingResult bindingResult ) throws Exception {
        if(bindingResult.hasErrors()){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean isPoint = GeoJsonUtils.isPoint(userLocDTO.getPointJson());
        if(!isPoint){
            throw new CustomException(CustomErrorCodeEnum.GEOJSON_TYPE_NOT_FIX,"应为Point类型");
        }
        List<User> users = userService.listUsersByLoc(userLocDTO);
        List<UserVO> userVOs = BeanArrayUtils.copyListProperties(users,UserVO.class);
        return userVOs;
    }

    @RequestMapping("queryUsersByTags")
    public List<UserVO> queryUsersByTag(List<Integer> tagIdList) throws Exception {
        for (Integer integer : tagIdList) {
            if(integer == null){
                throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
            }
        }
        List<User> users = userService.listUsersByTagList(tagIdList);
        List<UserVO> userVOs = BeanArrayUtils.copyListProperties(users,UserVO.class);
        return userVOs;
    }

    @RequestMapping("queryFriends")
    public List<FriendListVO> queryFriends(Integer userId) throws Exception {
        if(userId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        List<FriendListVO> friendList = userService.listFriendsByUserId(userId);
        return friendList;
    }

    @RequestMapping("requestFriend")
    public Boolean requestFriend
            (@Valid FriendRequest request, BindingResult bindingResult) throws Exception {
//        判断参数是否合法
        if(bindingResult.hasErrors()){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.addFriendRequest(request);
        return result;
    }

    @RequestMapping("queryFriendRequests")
    public List<FriendRequestListVO> queryFriendRequests
            (Integer userId) throws Exception {
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
            (Integer friendRequestId,Boolean reply) throws Exception {
        if(friendRequestId == null || reply == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.handleFriendRequest(friendRequestId, reply);
        return result;
    }

    @RequestMapping("deleteFriend")
    public Boolean deleteFriend
            (Integer myid, Integer friendId) throws Exception {
        if(myid == null || friendId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.deleteFriend(myid,friendId);
        return result;
    }

    @RequestMapping("addUserTag")
    public Boolean addUserTag(@Valid UserTag userTag, BindingResult bindingResult) throws CustomException {
        if(bindingResult.hasErrors()){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR);
        }
        Boolean result = userService.addUserTag(userTag);
        return result;
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
