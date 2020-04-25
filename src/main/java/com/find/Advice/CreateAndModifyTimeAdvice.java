package com.find.Advice;

import com.find.Util.Utils.AdviceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 *  利用aop自动处理插入与更新数据时的时间
 */
@Aspect
@Component
public class CreateAndModifyTimeAdvice {

    /**
     * 此为1.0版本设计，以service层的所有方法进行插入，不过不是很好，被废弃了
     * @param pjp
     * @return
     * @throws Throwable
     */
//    @Around("execution(* com.find.Service..*(..))")
//    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
//        Method method = AdviceUtils.getMethodFromPjp(pjp);
//        Object[] args = pjp.getArgs();
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        for (int i = 0; i < parameterAnnotations.length; i++) {
//            Annotation[] certainObjAnnos = parameterAnnotations[i];
//            for (int j = 0; j < certainObjAnnos.length; j++) {
//                Annotation anno = certainObjAnnos[j];
//                if(anno instanceof CreatePo){
//                    Object arg = args[i];
//                    Method getGmtCreated = arg.getClass().getMethod("setGmtCreated",LocalDateTime.class);
//                    getGmtCreated.invoke(arg, LocalDateTime.now());
//                    Method setGmtModified = arg.getClass().getMethod("setGmtModified",LocalDateTime.class);
//                    setGmtModified.invoke(arg, LocalDateTime.now());
//                }
//
//                if(anno instanceof ModifyPo){
//                    Object arg = args[i];
//                    Method setGmtModified = arg.getClass().getMethod("setGmtModified",LocalDateTime.class);
//                    setGmtModified.invoke(arg, LocalDateTime.now());
//                }
//            }
//        }
//        return pjp.proceed();
//    }

    /**
     *  从mapper插入，对于从基类继承下来的方法， 同样可以设置切点
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.find.mapper..insert(..))")
    public Object createPoAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Method method = AdviceUtils.getMethodFromPjp(pjp);
        Object[] args = pjp.getArgs();
//        实际上不允许length为0，继续执行会抛出异常
        if(args.length == 0){
            return pjp.proceed();
        }
        Object arg = args[0];
        Method getGmtCreated = arg.getClass().getMethod("setGmtCreated", LocalDateTime.class);
        getGmtCreated.invoke(arg, LocalDateTime.now());
        Method setGmtModified = arg.getClass().getMethod("setGmtModified",LocalDateTime.class);
        setGmtModified.invoke(arg, LocalDateTime.now());
        return pjp.proceed();
    }

    @Around("execution(* com.find.mapper..update(..))")
    public Object modifyPoUpdateAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Method method = AdviceUtils.getMethodFromPjp(pjp);
        Object[] args = pjp.getArgs();
//        实际上不允许length为0，继续执行会抛出异常
        if(args.length == 0){
            return pjp.proceed();
        }
        Object arg = args[0];

        Method setGmtCreated = arg.getClass().getMethod("setGmtCreated",LocalDateTime.class);
        setGmtCreated.invoke(arg, null);
        Method setGmtModified = arg.getClass().getMethod("setGmtModified",LocalDateTime.class);
        setGmtModified.invoke(arg, LocalDateTime.now());
        return pjp.proceed();
    }

    @Around("execution(* com.find.mapper..updateById(..))")
    public Object modifyPoUpdateByIdAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Method method = AdviceUtils.getMethodFromPjp(pjp);
        Object[] args = pjp.getArgs();
//        实际上不允许length为0，继续执行会抛出异常
        if(args.length == 0){
            return pjp.proceed();
        }
        Object arg = args[0];
//        Method setGmtCreated = arg.getClass().getMethod("setGmtCreated",LocalDateTime.class);
//        setGmtCreated.invoke(arg, null);
        Method setGmtModified = arg.getClass().getMethod("setGmtModified",LocalDateTime.class);
        setGmtModified.invoke(arg, LocalDateTime.now());
        return pjp.proceed();
    }
}
