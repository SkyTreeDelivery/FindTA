package com.find.Util.TypeConverter;

import com.find.Util.Enum.EnumImp.ControlEnum.FriendRequestHandleEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.GenderEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.SignStatusEnum;
import com.find.Util.Utils.JsonUtils;
import org.locationtech.jts.geom.Geometry;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateBinders{

    public static class GeoBinder extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Geometry geom = JsonUtils.geoJsonToGeom(text);
            geom.setSRID(4326);
            setValue(geom);
        }
    }

    public static class FriendRequestHandleEnumBinder extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer code = null;
            try {
                code = Integer.parseInt(text);
            }catch (Exception e){
                setValue(null);
            }
            setValue(FriendRequestHandleEnum.valueOf(code));
        }
    }

    public static class GenderEnumBinder extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer code = null;
            try {
                code = Integer.parseInt(text);
            }catch (Exception e){
                setValue(null);
            }
            setValue(GenderEnum.valueOf(code));
        }
    }

    public static class SignStatusEnumBinder extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer code = null;
            try {
                code = Integer.parseInt(text);
            }catch (Exception e){
                setValue(null);
            }
            setValue(SignStatusEnum.valueOf(code));
        }
    }

    public static class LocalDateTimeBinder extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                TemporalAccessor parse = dateTimeFormatter.parse(text);
                LocalDateTime dateTime = LocalDateTime.from(parse);
                setValue(dateTime);
            }catch (Exception e){
                setValue(null);
            }
        }
    }
}
