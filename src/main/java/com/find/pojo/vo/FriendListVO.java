package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendListVO implements Serializable {
    @JSONField(name = "friendRelationId",ordinal = 0)
    private Integer id;
    @JSONField(name = "friendUserId")
    private Integer friendId;

    private String username;
    private String nickname;
    private Integer gender;
    private String faceImage;
}
