package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestListVO  implements Serializable {
    @JSONField(name = "friendRequestId",ordinal = 0)
    private Integer id;
    private Integer sendUserId;
    private String requestMessage;

    private String username;
    private String nickname;
    private String faceImage;
}
