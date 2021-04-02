package com.example.springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
   
   @RequestMapping("/login")
   public String login(){
      return "login";
   }

   @RequestMapping("/")
   public String index(){
      return "Index";
   }

   @RequestMapping("/admin")
   public String admin(){
      return "admin";
   }
}
