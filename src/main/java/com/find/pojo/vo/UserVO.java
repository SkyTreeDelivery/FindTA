package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    @JSONField(ordinal = 0)
    private Integer id;
    private String username;
    private Integer age;
    private Integer gender;
    private String faceImage;
    private String faceImageBig;
    private String nickname;
    private String email;
    @JSONField(serializeUsing = GeoCodc.class,deserializeUsing = GeoCodc.class)
    private Point location;
    private String phone;
}
