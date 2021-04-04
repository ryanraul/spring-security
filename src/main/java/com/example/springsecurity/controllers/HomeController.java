package com.example.springsecurity.controllers;

import com.example.springsecurity.model.User;
import com.example.springsecurity.services.SSUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
   
   @Autowired
   private SSUserDetailsService userDetailsService;
   
   @GetMapping("/login")
   @ResponseBody
   public ResponseEntity<User> login(){
      return ResponseEntity.ok().body(userDetailsService.getUsernameLogged());
   }

   @RequestMapping("/")
   public String index(){
      return "index";
   }

   @RequestMapping("/admin")
   public String admin(){
      return "admin";
   }

   @RequestMapping("/secure")
   public String secure(){
      return "secure";
   }

}
