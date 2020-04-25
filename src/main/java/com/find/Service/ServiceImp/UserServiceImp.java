package com.find.Service.ServiceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.ControlEnum.FriendRequestHandleEnum;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.SignStatusEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.*;
import com.find.mapper.*;
import com.find.pojo.dto.DtoPo.FriendRequestDTO;
import com.find.pojo.dto.DtoPo.UserDTO;
import com.find.pojo.dto.DtoPo.UserTagDTO;
import com.find.pojo.dto.MessageDTO;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.po.*;
import com.find.pojo.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserTagMapper userTagMapper;

    @Resource
    TagMapper tagMapper;

    @Resource
    FriendMapper friendMapper;

    @Resource
    RequestMapper requestMapper;

    @Resource
    MessageMapper messageMapper;

    @Resource
    ChatPartMapper chatPartMapper;

    @Resource
    FastDFSClient fastDFSClient;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserVO register(UserDTO userDTO) throws CustomException {
        //2. 如果username，email已被注册，则提示已占用
        if(usernameIsExist(userDTO.getUsername())
                ||emailIsExist(userDTO.getEmail())){
            throw new CustomException(CustomErrorCodeEnum.USER_HAS_EXISTED);
        }

        User user = BeanArrayUtils.copyProperties(userDTO, User.class);
        //3. 注册用户，注册完成后自动登录，返回token
        String token = getUniToken();
        user.setToken(token);
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        userMapper.insert(user);
        User newUser = getUserByUsername(user.getUsername());
        UserVO userVO = BeanArrayUtils.copyProperties(newUser, UserVO.class);
        return userVO;
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
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserVO login(String username, String password, String cid) throws CustomException {
//        2. 验证用户
        Boolean result = verifyUser(username, password);
        if(!result){
            throw new CustomException(CustomErrorCodeEnum.USER_LOGIN_ERROR);
        }
        User userinfo = getUserByUsername(username);
//        3. 生成新的用户token
        String token = getUniToken();
        User updateUser = new User();
        updateUser.setId(userinfo.getId());
        updateUser.setToken(token);
        updateUser.setCid(userinfo.getCid());
        updateUser(updateUser);
        if(!cid.equals(userinfo.getCid())){
            //TODO 通知原来登录的设备下线
        }
        User newUser = getUserByUsername(username);
        UserVO userVO = BeanArrayUtils.copyProperties(newUser, UserVO.class);
        return userVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean logout(String token) throws CustomException {
        User user = getUserByToken(token);
        if(user == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setToken(ValidateUtils.DEFAULT_TOKEN);
        updateUser(updateUser);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean updateUser(User user) {
//        md5加密
        if(user.getPassword() != null){
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        }

        return userMapper.updateById(user) == 1;
    }

    @Override
    public Boolean uploadFaceImage(String faceImageBase64, Integer userId) throws Exception {
        String tempDir =  "c:/" + "temp";
        String tempFaceImagePath = tempDir + "/" + userId + "_faceImage.png";
        File dir = new File(tempDir);
        if(!dir.exists()){
            dir.mkdir();
        }
        FileUtils.base64ToFile(tempFaceImagePath,faceImageBase64);

        MultipartFile faceImageMultiPart = FileUtils.fileToMultipart(tempFaceImagePath);
        if(faceImageMultiPart == null){
            return false;
        }
        String url = fastDFSClient.uploadBase64(faceImageMultiPart);
        System.out.println(url);
//        String thump = "_80x80.";
//        String[] arr = url.split("\\.");
//        String thumpImgUrl = arr[0] + thump + arr[1];
        int width = 80;
        int height = 80;
        String thumbnailPath = "c:/temp/"+ userId + "_faceImage_"+ width + "x" + height + ".png";
        ImageUtils.genarateThumbnails(tempFaceImagePath,width,height,thumbnailPath);

        MultipartFile faceImageThunbnailMultiPart = FileUtils.fileToMultipart(thumbnailPath);
        if(faceImageThunbnailMultiPart == null){
            return false;
        }
        String url_thumb = fastDFSClient.uploadBase64(faceImageThunbnailMultiPart);

        User user = new User();
        user.setId(userId);
        user.setFace_image(url_thumb);
        user.setFace_image_big(url);
        return updateUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        return userMapper.selectOne(wrapper);
}

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByToken(String token) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("token",token);
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> listUsersByNickname(String nickname) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("nickname",nickname);
        return userMapper.selectList(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> listUsersByLoc(UserLocDTO userLocDTO) {
        return userMapper.listUserByLoc(userLocDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> listUsersByTagId(Integer tagId) {
        QueryWrapper<UserTag> wrapper = new QueryWrapper();
        wrapper.eq("tag_id",tagId);
        List<UserTag> userTags = userTagMapper.selectList(wrapper);
        List<Integer> userIdList = userTags.stream()
                .map(UserTag::getUserId)
                .collect(Collectors.toList());
        List<User> users = userMapper.selectBatchIds(userIdList);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean tokenIsExist(String token) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("token",token);
        return userMapper.selectCount(wrapper) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean userIsExist(Integer userId) {
        return userMapper.selectById(userId) != null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean usernameIsExist(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectCount(wrapper) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean emailIsExist(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        return userMapper.selectCount(wrapper) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean removeUserById(Integer userId) {
        return userMapper.deleteById(userId) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean verifyToken(String token) {
        User user = getUserByToken(token);
        return user != null;
    }

    /*=========================== friend ===============================*/
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addFriend( Friend friend) {
        return friendMapper.insert(friend) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FriendListVO> listFriendsByUserId(Integer userId) throws Exception {
//        查询朋友关系
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("my_id",userId);
        List<Friend> friends = friendMapper.selectList(wrapper);
        if(friends.size() == 0){
            return null;
        }
        List<FriendListVO> friendListVOS = BeanArrayUtils.copyListProperties(friends, FriendListVO.class);

//        利用朋友关系查询user
        if(friendListVOS == null) return null;
        List<Integer> friendIds = friendListVOS.stream()
                .map(FriendListVO::getFriendId)
                .collect(Collectors.toList());
        List<User> friendUsers = userMapper.selectBatchIds(friendIds);
        if(friendIds.size() != friendUsers.size()){
            throw new CustomException(CustomErrorCodeEnum.DATA_NOT_MATCHING);
        }

        Map<Integer, User> idUserMap = friendUsers.stream()
                .collect(Collectors.toMap(User::getId, (e) -> e));
        for (FriendListVO friendListVO : friendListVOS) {
            User user = idUserMap.get(friendListVO.getFriendId());
            BeanUtils.copyProperties(user,friendListVO,"id");
        }
        return friendListVOS;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Friend getFriendById(Integer friendId) {
        return friendMapper.selectById(friendId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Friend getFriendBySendIdAndAcceptId(Integer sendUserId, Integer acceptUserId) {
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("friend_id",sendUserId)
                .eq("my_id",acceptUserId);
        return friendMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean removeFriend(Integer myId, Integer friendId) throws CustomException {
        if(getFriendBySendIdAndAcceptId(myId,friendId) == null){
            throw new CustomException(CustomErrorCodeEnum.NO_FRINEND_RELATION);
        }
//        删除自己的好友
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("my_id",myId)
                .eq("friend_id",friendId);
        int delete = friendMapper.delete(wrapper);
//        删除对方的好友并通知对方
        QueryWrapper<Friend> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("my_id",friendId)
                .eq("friend_id",myId);
        int delete1 = friendMapper.delete(wrapper1);
        return delete == 1 && delete1 == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean friendIsExist(Integer friendRelationId) {
        return friendMapper.selectById(friendRelationId) != null;
    }

    /*=========================   message      ============================= */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addMessage(Message message) {
        String chatPartStr = StringUtils.convertChatPart(message.getSendUserId(), message.getAcceptUserId());
        ChatPart chatPart = getChatPartByStrPart(chatPartStr);
//        如果不存在，则先添加chatPart
        if(chatPart == null){
            addChatPart(message.getSendUserId(),message.getAcceptUserId());
            chatPart = getChatPartByStrPart(chatPartStr);
            if(chatPart == null){
                return false;
            }
        }
//        添加message
        Integer msgCount = chatPart.getMsgSum();
        message.setPartId(chatPart.getId());
        message.setMsgIndex(msgCount++);
        Boolean result = messageMapper.insert(message) == 1;
        if(result){
//            更新chatPart
            chatPart.setMsgSum(msgCount);
            return updateChatPart(chatPart);
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Message getMessageById(Integer messageId) {
        return messageMapper.selectById(messageId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageVO> listChatMsg(MessageDTO messageDTO) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        String chatPartStr = StringUtils.convertChatPart(messageDTO.getSendUserId(), messageDTO.getAcceptUserId());
        ChatPart chatPart = getChatPartByStrPart(chatPartStr);
        wrapper.eq("part_id",chatPart.getId())
                .le("gmt_created",messageDTO.getEndTime())
                .orderByDesc("msg_index")
                .last("limit " + messageDTO.getMessageCount());

//        已废弃
//        wrapper.and(e->{
//            e.eq("send_user_id",messageDTO.getSendUserId())
//                    .eq("accept_user_id",messageDTO.getAcceptUserId());
//        }).or(e->{
//            e.eq("send_user_id",messageDTO.getAcceptUserId())
//                    .eq("accept_user_id",messageDTO.getSendUserId());
//        })
//                .le("gmt_created",messageDTO.getEndTime())
//                .orderByDesc("gmt_created")
//                .last("limit " + messageDTO.getMessageCount());
        List<Message> messages = messageMapper.selectList(wrapper);
        List<Message> messageSort = messages.stream()
                .sorted((a, b) -> {
//                    默认升序
                    return a.getMsgIndex().compareTo(b.getMsgIndex());
                }).collect(Collectors.toList());
        List<MessageVO> messageVOS = BeanArrayUtils.copyListProperties(messageSort, MessageVO.class);
        return messageVOS;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageVO> listAllUnSignMessage(Integer userId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("accept_user_id",userId)
                .eq("sign_flag", SignStatusEnum.UNSIGN.code);
        List<Message> messages = messageMapper.selectList(wrapper);
        List<MessageVO> messageVOS = BeanArrayUtils.copyListProperties(messages, MessageVO.class);
        return messageVOS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean signMessage(List<Integer> ids) {
        List<Message> messages = messageMapper.selectBatchIds(ids);
        messages.forEach(m->{
            m.setSignFlag(SignStatusEnum.SIGNED.code);
            messageMapper.updateById(m);
        });
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean signAllMessages(Integer userId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("accept_user_id",userId)
                .eq("sign_flag", SignStatusEnum.UNSIGN.code);
        List<Message> messages = messageMapper.selectList(wrapper);
        for (Message message : messages) {
            message.setSignFlag(SignStatusEnum.SIGNED.code);
            messageMapper.updateById(message);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean removeMessageById(Integer messageId) {
        return userMapper.deleteById(messageId) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean messageIsExist(Integer messageId) {
        return messageMapper.selectById(messageId) != null;
    }

    /*=========================   chatPart      ============================= */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addChatPart(Integer userAId, Integer userBId) {
        String chatPartStr = StringUtils.convertChatPart(userAId, userBId);
        ChatPart c = getChatPartByStrPart(chatPartStr);
//        如果不存在，才添加
        if(c == null){
            ChatPart chatPart = new ChatPart();
            chatPart.setUserPart(chatPartStr);
            chatPart.setMsgSum(0);
            return chatPartMapper.insert(chatPart) != 0;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean updateChatPart(ChatPart chatPart) {
        return chatPartMapper.updateById(chatPart) != 0;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ChatPart getChatPartById(Integer chatPartId) {
        return chatPartMapper.selectById(chatPartId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ChatPart getChatPartByStrPart(String strPart) {
        QueryWrapper<ChatPart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_part",strPart);
        return chatPartMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean chatPartIsExist(Integer chatPart) {
        return chatPartMapper.selectById(chatPart) != null;
    }

    /*=========================   friendrequest      ============================= */

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addFriendRequest( FriendRequestDTO requestDTO) throws CustomException {
        //        判断是否添加的是自己
        if(requestDTO.getSendUserId().equals(requestDTO.getAcceptUserId())){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_CANT_ADD_SELF);
        }
//        判断是否已经添加过好友
        if(getFriendBySendIdAndAcceptId(requestDTO.getSendUserId(),requestDTO.getAcceptUserId()) != null){
            throw new CustomException(CustomErrorCodeEnum.FRIEND_IS_EXIST);
        }


        FriendRequest request = BeanArrayUtils.copyProperties(requestDTO, FriendRequest.class);
//        是否已经发送过请求,如已经发送过请求，则更新请求message
        QueryWrapper<FriendRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("send_user_id",request.getSendUserId())
                 .eq("accept_user_id",request.getAcceptUserId());
        FriendRequest friendRequest = requestMapper.selectOne(wrapper);
        if(friendRequest != null){
            FriendRequest update = new FriendRequest();
            update.setId(friendRequest.getId());
            update.setRequestMessage(request.getRequestMessage());
            return requestMapper.updateById(update) == 1;
        }
//        添加好友
        return requestMapper.insert(request) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public FriendRequest getFriendRequestById(Integer frid) {
        return requestMapper.selectById(frid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FriendRequestListVO> listFriendRequestsByUserId(Integer userId) throws Exception{
        QueryWrapper<FriendRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("accept_user_id",userId);
        List<FriendRequest> friendRequests = requestMapper.selectList(wrapper);
        if(friendRequests.size() == 0){
            return null;
        }
        List<FriendRequestListVO> friendRequestListVOList
                = BeanArrayUtils.copyListProperties(friendRequests, FriendRequestListVO.class);

        List<Integer> sendUserIds = friendRequests.stream()
                .map(FriendRequest::getSendUserId)
                .collect(Collectors.toList());
        List<User> users = userMapper.selectBatchIds(sendUserIds);
        if(sendUserIds.size() != users.size()){
            throw new CustomException(CustomErrorCodeEnum.DATA_NOT_MATCHING);
        }
        Map<Integer, User> idUserMap = users.stream()
                .collect(Collectors.toMap((e) -> e.getId(), (e) -> e));
        assert friendRequestListVOList != null;
        for (FriendRequestListVO friendRequestListVO : friendRequestListVOList) {
            User user = idUserMap.get(friendRequestListVO.getSendUserId());
            BeanUtils.copyProperties(user,friendRequestListVO);
        }
        return friendRequestListVOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean handleFriendRequest(Integer friendRequestId, FriendRequestHandleEnum reply) throws CustomException {
        FriendRequest friendRequest = getFriendRequestById(friendRequestId);
        //接收请求，删除好友请求，添加好友关系
        if(reply.equals(FriendRequestHandleEnum.REQUEST_FRIEND)){
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
            addFriend(friendReverse);
            return true;
        }else{
            //拒绝请求
            return removeFriendRequestById(friendRequestId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean removeFriendRequestById(Integer frid) {
        return requestMapper.deleteById(frid) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean friendRequestIsExist(Integer friendRequestId) {
        return requestMapper.selectById(friendRequestId) != null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addUserTag(UserTagDTO userTagDTO) throws CustomException {
        if(getUserById(userTagDTO.getUserId()) == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        UserTag userTag = BeanArrayUtils.copyProperties(userTagDTO, UserTag.class);
        return userTagMapper.insert(userTag) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserTag getUserTagById(Integer userTagId) {
        return userTagMapper.selectById(userTagId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserTagVO> listTagsByUserId(Integer userId) throws CustomException {
        if(getUserById(userId) == null){
            throw new CustomException(CustomErrorCodeEnum.USER_NOT_EXIST);
        }
        QueryWrapper<UserTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<UserTag> userTags = userTagMapper.selectList(wrapper);
        List<UserTagVO> userTagVOS = BeanArrayUtils.copyListProperties(userTags, UserTagVO.class);
        List<Integer> tagIds = userTags.stream()
                .map(UserTag::getTagId)
                .collect(Collectors.toList());
        List<Tag> tags = tagMapper.selectBatchIds(tagIds);
        if(tagIds.size() != tags.size()){
            throw new CustomException(CustomErrorCodeEnum.DATA_NOT_MATCHING);
        }
        Map<Integer, Tag> idTagMap = tags.stream()
                .collect(Collectors.toMap((e) -> e.getId(), e -> e));
        assert userTagVOS != null;
        for (UserTagVO userTagVO : userTagVOS) {
            Tag tag = idTagMap.get(userTagVO.getTagId());
            BeanUtils.copyProperties(tag,userTagVO);
        }
        return userTagVOS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean removeUserTagById(Integer userTagId) {
        return userTagMapper.deleteById(userTagId) == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean userTagIsExist(Integer userTagId) {
        return userTagMapper.selectById(userTagId) != null;
    }
}
