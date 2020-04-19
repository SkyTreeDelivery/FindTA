package com.find.Util.Utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanArrayUtils {
    public static <T> List<T> copyListProperties(List<?> source, Class<T> targetBean) {
        List<T> targetList = new ArrayList<>();
        for (Object obj : source) {
            try {
                T tarObj = null;
                tarObj = targetBean.newInstance();
                BeanUtils.copyProperties(obj,tarObj);
                targetList.add(tarObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(targetList.size() > 0){
            return targetList;
        }
        return null;
    }


    public static <T> T copyProperties(Object source, Class<T> targetBean){
        try {
            T tarObj = targetBean.newInstance();
            BeanUtils.copyProperties(source,tarObj);
            return tarObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 }
