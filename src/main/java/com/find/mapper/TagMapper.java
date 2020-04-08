package com.find.mapper;

import com.find.pojo.Tag;

import java.util.List;

public interface TagMapper {
    Boolean doCreateTag(Tag tag);

    Boolean updateTag(Tag tag);

    Tag getTagById(Integer tagId);

    List<Tag> listAll();

    List<Tag> listTagsByGroup(Integer groupId);

    Boolean deleteTag(Integer tagId);
}
