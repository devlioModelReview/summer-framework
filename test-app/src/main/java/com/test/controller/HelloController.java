package com.test.controller;

import com.itranswarp.summer.annotation.Controller;
import com.itranswarp.summer.annotation.GetMapping;
import com.itranswarp.summer.annotation.RequestParam;
import com.itranswarp.summer.web.ModelAndView;

@Controller
public class HelloController {
    
    @GetMapping("/hello")
    public ModelAndView hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }
    
    @GetMapping("/api/hello")
    public String apiHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "{\"message\": \"Hello, \" + name + \"!\"}";
    }
}
