package com.find.Service.ServiceImp;

import com.find.Service.TagGroupService;
import com.find.mapper.TagGroupMapper;
import com.find.pojo.PO.TagGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagGroupServiceImp implements TagGroupService {

    @Resource
    TagGroupMapper tagGroupMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TagGroup> listAll() {
        return tagGroupMapper.selectList(null);
    }
}
