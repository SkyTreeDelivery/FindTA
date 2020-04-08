package com.find.Service.ServiceImp;

import com.find.Service.TagGroupService;
import com.find.mapper.TagGroupMapper;
import com.find.pojo.TagGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagGroupServiceImp implements TagGroupService {

    @Resource
    TagGroupMapper tagGroupMapper;

    @Override
    public List<TagGroup> listAll() {
        return tagGroupMapper.listAll();
    }
}
