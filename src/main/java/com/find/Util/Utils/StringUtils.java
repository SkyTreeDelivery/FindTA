package com.find.Util.Utils;

public class StringUtils {
    
    public static Boolean isBlank(String str){
        return str == null || str.equals("");
    }

    public static String convertChatPart(Integer userAId, Integer userBId){
        if(userAId < userBId){
            return userAId + "-" + userBId;
        }else{
            return userBId + "-" + userAId;
        }
    }

}
