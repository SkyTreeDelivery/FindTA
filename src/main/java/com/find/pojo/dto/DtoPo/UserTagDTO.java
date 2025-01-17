package com.find.pojo.dto.DtoPo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTagDTO implements Serializable {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer tagId;
}
