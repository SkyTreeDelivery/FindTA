package com.find.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private Integer id;
    private String username;
    private Integer age;
    private boolean gender;
    private String face_image;
    private String face_image_big;
    private String pickname;
    private String email;
    private String location;
    private String phone;
}
