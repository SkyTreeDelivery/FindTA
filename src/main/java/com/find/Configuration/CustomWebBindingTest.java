package com.find.Configuration;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

public class CustomWebBindingTest implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.registerCustomEditor(Point.class, new DateBinders.GeoBinder());
    }
}
