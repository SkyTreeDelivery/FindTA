package com.find.Service.ServiceImp;

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
        return tagMapper.listTagsByGroup(groupId);
    }

    @Override
    public List<Tag> listAll() {
        return tagMapper.listAll();
    }

    public Tag getTagById(Integer tagId){
        return tagMapper.getTagById(tagId);
    }
}
