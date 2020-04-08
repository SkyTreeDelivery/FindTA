package com.find.Util.Utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanArrayUtils {
    public static <T> List<T> copyListProperties(List<?> source, Class<T> targetBean) throws IllegalAccessException, InstantiationException {
        List<T> targetList = new ArrayList<>();
        for (Object obj : source) {
            T tarObj = targetBean.newInstance();
            BeanUtils.copyProperties(obj,tarObj);
            targetList.add(tarObj);
        }
        return targetList;
    }


    public static <T> T copyProperties(Object source, Class<T> targetBean) throws IllegalAccessException, InstantiationException {
        T tarObj = targetBean.newInstance();
        BeanUtils.copyProperties(source,tarObj);
        return tarObj;
    }
 }
