package com.find.Service.ServiceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.find.Service.TagService;
import com.find.mapper.TagMapper;
import com.find.pojo.Tag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImp implements TagService {

    @Resource
    TagMapper tagMapper;

    @Override
    public List<Tag> listTagsByTagGroup(Integer groupId) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("tg_id",groupId);
        return tagMapper.selectList(wrapper);
    }

    @Override
    public List<Tag> listAll() {
        return tagMapper.selectList(null);
    }

    public Tag getTagById(Integer tagId){
        return tagMapper.selectById(tagId);
    }
}
