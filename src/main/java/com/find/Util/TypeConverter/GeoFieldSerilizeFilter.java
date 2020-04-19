package com.find.Util.TypeConverter;

//
//public class GeoFieldSerilizeFilter implements ValueFilter {
//    @Override
//    public Object process(Object obj, String name, Object value) {
//        Boolean flag = false;
//        try {
//            Field field = obj.getClass().getDeclaredField(name);
//            // 获取注解
//            GeoField annotation = field.getAnnotation(GeoField.class);
//            if(annotation != null && annotation.value()){
//                flag = true;
//            }
//            if (flag == true && value != null) {
//                // 这里 其他类型转换成String类型
//                value = value.toString();
//            }
//        } catch (Exception e) {
//            return value;
//        }
//        return value;
//    }
//}
