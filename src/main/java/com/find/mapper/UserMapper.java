package com.find.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.find.pojo.po.User;
import com.find.pojo.dto.UserLocDTO;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*====================== User ==============================*/
    List<User> listUserByLoc(UserLocDTO userLocDTO);
}
