package com.find;

import com.find.Service.UserService;
import com.find.Util.Enum.EnumImp.POJOEnum.GenderEnum;
import com.find.Util.Enum.EnumImp.POJOEnum.SignStatusEnum;
import com.find.Util.Exception.CustomException;
import com.find.Util.Utils.JsonUtils;
import com.find.Util.Utils.StringUtils;
import com.find.Util.Utils.ValidateUtils;
import com.find.mapper.UserMapper;
import com.find.pojo.PO.Message;
import com.find.pojo.PO.User;
import com.find.pojo.dto.DtoPo.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//@SpringBootTest
class FindTaApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserService userService;


	@Test
	void contextLoads() {
		User user = new User();
		user.setUsername("zhang54");
		user.setPassword("zhang002508");
		user.setAge(50);
		user.setEmail("zhang11375154@sina.com");
		user.setGender(GenderEnum.MAN.code);
		user.setNickname("测试54");
		user.setCid(UUID.randomUUID().toString());
		user.setPhone("15555555558");
		user.setGmtCreated(LocalDateTime.now());
		user.setGmtModified(LocalDateTime.now());
		user.setLocation(null);
		userMapper.insert(user);
	}

	@Test
	public void testB(){
		User user = new User();
		user.setId(44);
		user.setPassword("zhang002508");
		userMapper.updateById(user);
	}

	@Test
	public void testC() throws UnsupportedEncodingException {
		String json = "{\n" +
				"    \"type\": \"Point\",\n" +
				"    \"coordinates\": [125.6, 10.1]\n" +
				" \n" +
				"}";
		String s = JsonUtils.geoJsonToWkt(json);
		System.out.println(s);
		String s1 = JsonUtils.wktToGeoJson(s);
		System.out.println(s1);
		String encode = URLEncoder.encode("{\"type\":\"Point\",\"coordinates\":[125.6,10.1]}", "UTF-8");
		System.out.println(encode);
		String decode = URLDecoder.decode(encode, "UTF-8");
		System.out.println(decode);


	}

	@Test
	public void testG(){
		String wkt = "LINESTRING (254058.76074485347 475001.2186020431, 255351.04293761664 474966.9279243938)";
		String s = JsonUtils.wktToGeoJson(wkt);
		System.out.println(s);
	}

	@Test
	public void testH(){
		int i = Integer.parseInt("");
		System.out.println(i);
		int i1 = Integer.parseInt(null);
		System.out.println(i1);
		int zh = Integer.parseInt("zh");
		System.out.println(zh);
	}

	@Test
	public void testI(){
		User user = userMapper.selectById(null);
		System.out.println(user);
	}

	@Test
	public void inesrtMessgaes(){
		List<Integer> userIds = Arrays.asList(49, 20, 9);
		for (int i = 0; i < userIds.size() - 1; i++) {
			Integer userAId = userIds.get(i);
			Integer userBId = userIds.get(i+1);
			for (int j = 0; j < 100; j++) {
				Message message = new Message();
				message.setSignFlag(SignStatusEnum.UNSIGN.code);
				if(j % 2 == 0){
                    message.setSendUserId(userAId);
                    message.setAcceptUserId(userBId);
                    message.setMessage( userBId + "与" + userAId + "的聊天记录：第" + j + "条");
                }else{
                    message.setSendUserId(userBId);
                    message.setAcceptUserId(userAId);
                    message.setMessage( userBId + "与" + userAId + "的聊天记录：第" + j + "条");
                }
				userService.addMessage(message);
			}
		}
	}

	@Test
	public void testJ() throws UnsupportedEncodingException {
		String encode = URLEncoder.encode("2020-04-14T21:45:10", "UTF-8");
		System.out.println(encode);
	}

	@Test
	public void testK(){
		String s = StringUtils.convertChatPart(1, 5);
		System.out.println(s);
		String s1 = StringUtils.convertChatPart(5, 1);
		System.out.println(s1);
		System.out.println(s1.endsWith(s));
	}

	@Test
    public void testL(){
	    userService.addChatPart(1,5);
    }

    @Test
	public void testM() throws CustomException {
		UserDTO user = new UserDTO();
		user.setUsername("null");
		user.setPassword("zhang002508");
		user.setAge(50);
		user.setEmail("zhang11375162@sina.com");
		user.setGender(GenderEnum.MAN.code);
		user.setNickname("测试62");
		user.setCid(UUID.randomUUID().toString());
		user.setPhone("15555555565");
		ValidateUtils.validObj(user);
	}
}
