package com.find.pojo.vo;

import com.find.Util.Annonation.GeoField;
import com.find.Util.Geometry.Point;
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
    @GeoField
    private Point location;
    private String phone;
}
