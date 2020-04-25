package com.find.Controller;

import com.find.Service.TagService;
import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;
import com.find.Util.Exception.CustomException;
import com.find.pojo.po.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/tag/*")
@RestController
public class TagController {

    @Resource
    TagService tagService;

    @RequestMapping("listAll")
    public List<Tag> listAll(){
        List<Tag> tags = tagService.listAll();
        return tags;
    }

    @RequestMapping("queryTagsByTagGroupId")
    public List<Tag> queryTagsByTagGroupId(Integer tagGroupId) throws CustomException {
        if(tagGroupId == null){
            throw new CustomException(CustomErrorCodeEnum.PARAM_VERIFY_ERROR
                    ,"tagGroupId不能为空");
        }
        List<Tag> tags = tagService.listTagsByTagGroup(tagGroupId);
        return tags;
    }
}
