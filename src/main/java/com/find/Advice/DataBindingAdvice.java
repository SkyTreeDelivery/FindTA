package com.find.Advice;

import com.find.Util.Enum.EnumImp.ControlEnum.FriendRequestHandleEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.GenderEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.SignStatusEnum;
import com.find.Util.TypeConverter.DateBinders;
import org.locationtech.jts.geom.Point;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDateTime;

@ControllerAdvice
public class DataBindingAdvice {
    @InitBinder
    public void dataBind(WebDataBinder binder){
        binder.registerCustomEditor(Point.class, new DateBinders.GeoBinder());
        binder.registerCustomEditor(GenderEnum.class,new DateBinders.GenderEnumBinder());
        binder.registerCustomEditor(SignStatusEnum.class,new DateBinders.SignStatusEnumBinder());
        binder.registerCustomEditor(FriendRequestHandleEnum.class,new DateBinders.FriendRequestHandleEnumBinder());
        binder.registerCustomEditor(LocalDateTime.class,new DateBinders.LocalDateTimeBinder());
    }

}
