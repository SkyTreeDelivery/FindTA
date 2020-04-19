package com.find.Advice;

//public class CreatePoAdvice implements MethodInterceptor {
//    @Override
//    public Object invoke(MethodInvocation mi) throws Throwable {
//        Method method = mi.getMethod();
//        Object[] args = mi.getArguments();
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        for (int i = 0; i < parameterAnnotations.length; i++) {
//            Annotation[] certainObjAnnos = parameterAnnotations[i];
//            for (int j = 0; j < certainObjAnnos.length; j++) {
//                Annotation anno = certainObjAnnos[j];
//                if(anno instanceof CreatePo){
//                    Object arg = args[i];
//                    Method getGmtCreated = arg.getClass().getMethod("setGmtCreated");
//                    getGmtCreated.invoke(arg, LocalDateTime.now());
//                    Method setGmtModified = arg.getClass().getMethod("setGmtModified");
//                    setGmtModified.invoke(arg, LocalDateTime.now());
//                }
//            }
//        }
//        return mi.proceed();
//    }
//}
