package com.find.Util.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public class AdviceUtils {

    public static Method getMethodFromPjp(ProceedingJoinPoint pjp){
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }
}
