package com.find;

import com.find.Util.Enum.EnumImp.POJOEnum.GenderEnum;
import com.find.pojo.po.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class ObjectTest {

    @Test
    public void testA() throws ClassNotFoundException {
        User user = new User();
        user.setUsername("zhang58");
        user.setPassword("zhang002508");
        user.setAge(50);
        user.setEmail("zhang11375158@sina.com");
        user.setGender(GenderEnum.MAN.code);
        user.setNickname("测试58");
        user.setCid(UUID.randomUUID().toString());
        user.setPhone("15555555561");
        user.setGmtCreated(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());

        Object obj = (Object) user;
        Class<?> aClass2 = obj.getClass();
        Class<? extends User> aClass = user.getClass();
        aClass.getName();
        Class<?> aClass1 = Class.forName(aClass.getName());
    }

}
