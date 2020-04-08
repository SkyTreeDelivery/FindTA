package com.find.pojo.dto;

import com.find.pojo.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatHandlerDTO {
    @NotNull(message = "handlerType不能为空")
    private Integer handlerType;

    private Message message;

    private List<Integer> ids;
}
