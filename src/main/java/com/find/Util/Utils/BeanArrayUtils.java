package com.find.Util.Utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanArrayUtils {
    /**
     * 作为service的返回值，可以直接返回，null意味着发生错误
     * @param sourceList
     * @param targetBean
     * @param <T>
     * @return 如果传入的sourceList的size =0 ，则返回的list size = 0，
     *          如在转换过程中发生了异常，则返回一个null
     */
    public static <T> List<T> copyListProperties(List<?> sourceList, Class<T> targetBean) throws IllegalAccessException, InstantiationException {
        List<T> targetList = new ArrayList<>();
        for (Object obj : sourceList) {
            T tarObj = null;
            tarObj = targetBean.newInstance();
            BeanUtils.copyProperties(obj,tarObj);
            targetList.add(tarObj);
        }
        // 如果传入的集合size = 0，则转换结果的size = 0
        return targetList;
    }


    public static <T> T copyProperties(Object source, Class<T> targetBean) throws IllegalAccessException, InstantiationException {
        if(source == null){
            throw new NullPointerException("source Obj 不能为空");
        }
        T tarObj = targetBean.newInstance();
        BeanUtils.copyProperties(source,tarObj);
        return tarObj;
    }
 }
