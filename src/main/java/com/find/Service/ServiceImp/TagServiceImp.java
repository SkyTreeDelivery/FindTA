package com.find.Service.ServiceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.find.Service.TagService;
import com.find.mapper.TagMapper;
import com.find.pojo.po.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImp implements TagService {

    @Resource
    TagMapper tagMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Tag> listTagsByTagGroup(Integer groupId) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("tg_id",groupId);
        return tagMapper.selectList(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean tagIsExist(Integer tagId) {
        return tagMapper.selectById(tagId) != null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Tag> listAll() {
        return tagMapper.selectList(null);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Tag getTagById(Integer tagId){
        return tagMapper.selectById(tagId);
    }
}
