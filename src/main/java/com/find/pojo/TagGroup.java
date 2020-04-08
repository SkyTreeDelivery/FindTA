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
@TableName("chat_tag_group")
public class TagGroup implements Serializable {

    private Integer id;

    @NotNull(message = "tagGroup名不能为空")
    @Size(min = 0,max =10,message = "tagGroup名要在2-10个字符范围内")
    private String tagGroupName;

    private Date gmtCreated;

    private Date gmtModified;
}
