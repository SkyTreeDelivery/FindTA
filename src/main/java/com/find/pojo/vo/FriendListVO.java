package com.find.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendListVO {
    private Integer id;
    private String nickname;
    private Integer gender;
    private String faceImage;
}
