package com.find;

import com.find.Util.Utils.BeanArrayUtils;
import com.find.pojo.po.User;
import com.find.pojo.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class BeanArrayTest {

    public static void main(String[] args) throws Exception {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            User user = new User();
            user.setId(i);
            users.add(user);
        }
        List<UserVO> userVOS = new ArrayList<>();
        List<UserVO> userVOList = BeanArrayUtils.copyListProperties(users, UserVO.class);
        for (UserVO userVO : userVOList) {
            System.out.println(userVO);
        }
        System.out.println("---------------------------------");
        User userT = new User();
        UserVO userVO = BeanArrayUtils.copyProperties(userT, UserVO.class);
        System.out.println(userVO);
    }

}
