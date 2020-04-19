package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendListVO {
    @JSONField(name = "friendRelationId")
    private Integer id;
    @JSONField(name = "friendUserId")
    private Integer friendId;

    private String nickname;
    private Integer gender;
    private String faceImage;
}
