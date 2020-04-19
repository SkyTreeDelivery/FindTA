package com.find.pojo.PO;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_user")
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private Integer gender;

    private String face_image;

    private String face_image_big;

    private String nickname;

    private String email;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point location;

    private String cid;

    private String phone;

//    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String token;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
