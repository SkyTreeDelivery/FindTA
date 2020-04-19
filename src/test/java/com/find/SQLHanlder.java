package com.find;

import com.find.Service.TagService;
import com.find.Service.UserService;
import com.find.mapper.*;
import com.find.pojo.PO.Tag;
import com.find.pojo.PO.TagGroup;
import com.find.pojo.PO.User;
import com.find.pojo.PO.UserTag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SQLHanlder {

    private final static Logger logger = LoggerFactory.getLogger(SQLHanlder.class);

    @javax.annotation.Resource
    UserMapper userMapper;

    @javax.annotation.Resource
    UserTagMapper userTagMapper;

    @javax.annotation.Resource
    TagMapper tagMapper;

    @javax.annotation.Resource
    FriendMapper friendMapper;

    @javax.annotation.Resource
    RequestMapper requestMapper;

    @javax.annotation.Resource
    MessageMapper messageMapper;

    @Resource
    TagGroupMapper tagGroupMapper;

    @Resource
    TagService tagService;

    @Autowired
    private UserService userService;


    @Test
    public void testA(){
        List<String> groups = Arrays.asList("运动", "音乐", "书籍", "电影");
        groups.forEach(group->{
            TagGroup tagGroup = new TagGroup();
            tagGroup.setTagGroupName(group);
            tagGroupMapper.insert(tagGroup);
        });
    }

    @Test
    public void testB(){
        List<String> exerciseGroup = Arrays.asList("篮球", "羽毛球", "游泳");
        exerciseGroup.forEach(e->{
            Tag tag = new Tag();
            tag.setTgId(1);
            tag.setTagContext(e);
            tagMapper.insert(tag);
        });

        List<String> musicGroup = Arrays.asList("轻音乐", "摇滚", "古典");
        musicGroup.forEach(e->{
            Tag tag = new Tag();
            tag.setTgId(2);
            tag.setTagContext(e);
            tagMapper.insert(tag);
        });

        List<String> bookGroup = Arrays.asList("人类衰退之后", "白夜行", "中国之治");
        bookGroup.forEach(e->{
            Tag tag = new Tag();
            tag.setTgId(3);
            tag.setTagContext(e);
            tagMapper.insert(tag);
        });

        List<String> movieGroup = Arrays.asList("僵尸世界大战", "指环王", "哈利波特");
        movieGroup.forEach(e->{
            Tag tag = new Tag();
            tag.setTgId(4);
            tag.setTagContext(e);
            tagMapper.insert(tag);
        });
    }

    @Test
    public void testD(){
        List<TagGroup> tagGroups = tagGroupMapper.selectList(null);
        List<Tag> tags = tagMapper.selectList(null);
        List<User> users = userMapper.selectList(null);
        users.forEach(user->{
            Integer userId = user.getId();
            tagGroups.forEach(tagGroup -> {
                List<Tag> certainTags = tagService.listTagsByTagGroup(tagGroup.getId());
                Integer tagId = certainTags.get((int) (Math.random() * certainTags.size())).getId();
                UserTag userTag = new UserTag();
                userTag.setTagId(tagId);
                userTag.setUserId(userId);
                userTagMapper.insert(userTag);
            });
        });
    }
}
