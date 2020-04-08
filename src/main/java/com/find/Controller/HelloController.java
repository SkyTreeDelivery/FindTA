package com.find.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public Map<String, String> hello(String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        return map;
    }

    @GetMapping("ajaxTest")
    public String ajaxTest(){
        return "ajax";
    }

    @RequestMapping("/chat")
    public String chat(){
        return "chat";
    }
}
