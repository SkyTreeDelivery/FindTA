package com.find;

import com.find.Util.Enum.EnumImp.GenderEnum;
import com.find.Util.Geometry.Point;
import com.find.mapper.UserMapper;
import com.find.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class FindTaApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	void contextLoads() {
		com.find.pojo.User user = new User();
		user.setUsername("zhang54");
		user.setPassword("zhang002508");
		user.setAge(50);
		user.setEmail("zhang11375154@sina.com");
		user.setGender(GenderEnum.MAN.code);
		user.setNickname("测试54");
		user.setCid(UUID.randomUUID().toString());
		user.setPhone("15555555558");
		user.setGmtCreated(new Date());
		user.setGmtModified(new Date());
		user.setLocation(new Point(10,10));
		userMapper.insert(user);
	}

}
