package com.springboot.controller;


import com.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
//@RestController
public class UserController {
    @RequestMapping(name = "Summa",value  = "/hi", method = RequestMethod.GET)
//    @GetMapping("h")
    public String get(){
        return "Hey, What's Up!!";
    }

    @GetMapping("user")
    public User set()
    {
        User u = new User(1,"Tom", "tom@gmail.com");

        return u;
    }


    @GetMapping("/name")
    public String param(@RequestParam String n){
        return "NAme:"+n;
    }
}
