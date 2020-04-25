package com.find.pojo.dto.DtoPo;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  implements Serializable {
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

    @NotNull(message = "nickname不能为空")
    @Size(max = 16,message = "nickname最大为16个字符")
    private String nickname;

    @Email
    private String email;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point location;

    @NotNull(message = "设备id不能为空")
    private String cid;

    private String phone;
}
