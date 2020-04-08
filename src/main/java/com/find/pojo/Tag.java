package com.find.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_tag")
public class Tag implements Serializable {
    private Integer id;

    @NotNull(message = "tgId不能为空")
    private Integer tgId;

    @NotNull(message = "tagContext不能为空")
    @Size(min = 2,max = 10,message = "tag名需要在2-10个字符范围内")
    private String tagContext;

    private Date gmtCreated;

    private Date gmtModified;
}
