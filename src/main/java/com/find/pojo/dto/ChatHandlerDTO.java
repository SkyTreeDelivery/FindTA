package com.find.pojo.dto;

import com.find.pojo.dto.DtoPo.MessageAcceptDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatHandlerDTO implements Serializable {
    @NotNull(message = "handleType不能为空")
    private Integer handleType;

    @NotNull(message = "token不能为空")
    private String token;

    private MessageAcceptDTO message;

    private List<Integer> ids;
}
