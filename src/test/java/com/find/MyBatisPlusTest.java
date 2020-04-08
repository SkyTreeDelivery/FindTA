package com.find;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.find.Util.Enum.AgeEnum;
import com.find.mapper.UserMapper;
import com.find.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
public class MyBatisPlusTest {

    private final static Logger logger = LoggerFactory.getLogger(MyBatisPlusTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() throws IOException {
        Resource resource = new DefaultResourceLoader().getResource("com/find/mybatis.cfg.xml");
        InputStream inputStream = resource.getInputStream();
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectList(null);
        logger.debug(users.toString());
    }

    @Test
    public void test1(){
        List<User> users = userMapper.selectList(null);
        logger.debug(users.toString());
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("zhang52");
        user.setPassword("zhang002508");
        user.setAge(50);
        user.setEmail("zhang11375152@sina.com");
        user.setGender(AgeEnum.MAN.code);
        user.setNickname("测试52");
        user.setCid(UUID.randomUUID().toString());
        user.setPhone("15555555557");
        user.setGmtCreated(new Date());
        user.setGmtModified(new Date());
        userMapper.insert(user);
        logger.debug(user.toString());
    }

    @Test
    public void updateTest(){
        User user = new User();
        user.setId(29);
        user.setPassword("666666");
        user.setGender(AgeEnum.WOMAN.code);
        userMapper.updateById(user);
    }

    @Test
    public void updateWrapperTest(){
        User user = new User();
        user.setPassword("88888");
        user.setGender(AgeEnum.WOMAN.code);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",29);
        userMapper.update(user,queryWrapper);
    }

    @Test
    public void updateUpdateWrapperTest(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",29).set("password",9999999).set("gender",AgeEnum.MAN.code);
        userMapper.update(null,updateWrapper);
    }


    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(29);
        logger.debug("result ==>" + result);
    }

    @Test
    public void testDelete(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id" , 5);
        queryWrapper.eq("password","zhang002508");
        int result = userMapper.delete(queryWrapper);
        logger.debug("reslut ==>" + result);
    }
    @Test
    public void testDeleteByWrapper(){
//        推荐使用这种方法,不需要记忆查询字段数据路column name
        User user = new User();
        user.setId(15);
        user.setPassword("zhang002508");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        int result = userMapper.delete(queryWrapper);
        logger.debug("reslut ==>" + result);

    }

    @Test
    public void testDeleteMap(){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("id",6);
        queryMap.put("password","zhang002508");
        int result = userMapper.deleteByMap(queryMap);
        logger.debug("reslut ==>" + result);
    }

    @Test
    public void testDeleteByBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(10, 11, 12));
        logger.debug("reslut ==>" + result);
    }

    @Test
    public void testSecletById(){
        User user = userMapper.selectById(1);
        if(user != null){
            logger.debug(user.toString());
        }
    }

    @Test
    public void testSelectList(){
        User queryUser = new User();
        queryUser.setPassword("zhang002508");
        QueryWrapper<User> wrapper = new QueryWrapper<>(queryUser);
        List<User> users = userMapper.selectList(wrapper);
        logger.debug("users ==>" + users.toString());
    }

    @Test
    public void testSelectOne(){
        User queryUser = new User();
        queryUser.setId(1);
        QueryWrapper<User> wrapper = new QueryWrapper<>(queryUser);
        User user = userMapper.selectOne(wrapper);
        if(user != null){
            logger.debug("user ==>"  + user.toString());
        }
    }

    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(20, 21, 22));
        logger.debug("users ==>" + users.toString());
    }

    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(0,2);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("password", "zhang");
        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
        logger.debug("total ==>" + userIPage.getTotal());
        logger.debug("size ==>" + userIPage.getSize());
        logger.debug("current ==> " + userIPage.getCurrent());
        logger.debug("pages ==> " + userIPage.getPages());
        List<User> records1 = userIPage.getRecords();
        List<User> records2 = userIPage.getRecords();
        logger.debug("result ==> " + records1.toString());
        logger.debug("result ==> " + records2.toString());
    }
}