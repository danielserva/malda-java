package com.danielserva.malda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping("/")
    public @ResponseBody String helloFromMalda(){
        return "Welcome to Malda, a simple Malware detection api written in java";
    }
}
