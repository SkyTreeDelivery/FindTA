package com.find.mapper;

import com.find.pojo.TagGroup;

import java.util.List;

public interface TagGroupMapper {
    Boolean doCreateTagGroup(TagGroup tagGroup);

    Boolean updateTagGroup(TagGroup tagGroup);

    List<TagGroup> listAll();

    Boolean deleteTagGroup(Integer tgId);
}
