package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestListVO {
    @JSONField(name = "friendRequestId")
    private Integer id;
    private Integer sendUserId;
    private String requestMessage;

    private String nickname;
    private String faceImage;
}
