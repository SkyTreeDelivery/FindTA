package com.find.pojo.dto;

import com.find.pojo.PO.Message;
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
    @NotNull(message = "handlerType不能为空")
    private Integer handlerType;

    private Integer connectUserId;

    private Message message;

    private List<Integer> ids;
}
