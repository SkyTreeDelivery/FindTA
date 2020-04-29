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
public class UserSearchVO implements Serializable {
    private Integer id;
    private String username;
    private Integer age;
    private Integer gender;
    private String face_image;
    private String nickname;
    @JSONField(serializeUsing = GeoCodc.class)
    private Point location;
    private Double distance;
}
