package com.find.Service;

import com.find.pojo.Tag;

import java.util.List;

public interface TagService {

    Tag getTagById(Integer tagId);

    List<Tag> listAll();

    List<Tag> listTagsByTagGroup(Integer tagGroupName);

//    Boolean addTag(Tag tag);
//
//    Boolean saveTag(Tag tag);
//
//    Boolean removeTagById(Integer tagId);
}