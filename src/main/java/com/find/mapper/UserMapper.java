package com.find.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.find.pojo.dto.UserLocDTO;
import com.find.pojo.po.User;
import com.find.pojo.vo.UserSearchVO;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*====================== User ==============================*/
    List<UserSearchVO> listUserByLoc(UserLocDTO userLocDTO);
}
