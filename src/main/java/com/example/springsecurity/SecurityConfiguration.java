package com.example.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

   //Utilizado para 'encobrir' a senha caso alguem tente acessala pelo navegador
   @Bean
   public static BCryptPasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   //Caso não ocorra a autenticação sera rediricionado para a tela de login
   @Override
   protected void configure(HttpSecurity http) throws Exception{
      http.authorizeRequests().anyRequest().authenticated().and().formLogin();
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
      auth.inMemoryAuthentication().withUser("user")
      .password(passwordEncoder().encode("password")).authorities("USER");
   }

   
}
