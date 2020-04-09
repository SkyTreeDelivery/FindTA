package com.find.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.find.Util.Annonation.GeoField;
import com.find.Util.Geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_user")
public class User implements Serializable {

    private Integer id;

    @NotNull(message = "username不能为空")
    @Size(min = 6, max = 15,message = "用户名在6-15个字符范围内")
    private String username;

    @NotNull(message = "password不能为空")
    @Size(min = 6,max = 15,message = "密码在6-15个字符范围内")
    private String password;

    @Min(0)
    private Integer age;

    @NotNull(message = "gender不能为空")
    private Integer gender;

    private String face_image;

    private String face_image_big;

    @NotNull(message = "nickname不能为空")
    @Size(max = 16,message = "nickname最大为16个字符")
    private String nickname;

    @Email
    private String email;

    @GeoField
    private Point location;

    @NotNull(message = "设备id不能为空")
    private String cid;

    private String phone;

    private String token;

    @Past
    private Date gmtCreated;

    @Past
    private Date gmtModified;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
