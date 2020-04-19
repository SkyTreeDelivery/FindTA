package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

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
    @JSONField(serializeUsing = GeoCodc.class)
    private Point location;
    private String phone;
}
