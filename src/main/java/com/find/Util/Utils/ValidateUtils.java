package com.find.Util.Utils;

import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidateUtils {

    public static final String DEFAULT_TOKEN = "08dd226c-0df7-4764";

    private static final Logger logger = LoggerFactory.getLogger(ValidateUtils.class);

    public static void handlerValidateResult(BindingResult bindingResult) throws CustomException {
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(fieldError -> {
                //日志打印不符合校验的字段名和错误提示
                logger.error("error field is : {} ,message is : {}", fieldError.getField(), fieldError.getDefaultMessage());
            });
            Map<String, String> fieldMsgMap = errors.stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR,fieldMsgMap.toString());
        }
    }

    public static <T> Boolean validObj(T obj) throws CustomException {
        Set<ConstraintViolation<T>> result = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (!CollectionUtils.isEmpty(result)) {
            String messages = result.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((m1, m2) -> m1 + "；" + m2)
                    .orElse("参数输入有误！");
            logger.info(messages);
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR,messages);
        }
        return true;
    }
}
