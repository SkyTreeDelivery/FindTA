package com.find.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestListVO {
    private Integer sendUserId;
    private String nickname;
    private String sendUserFaceImage;
    private String requestText;
}
