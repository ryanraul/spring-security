package com.example.springsecurity;

import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.services.SSUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

   //Utilizado para 'encobrir' a senha caso alguem tente acessala pelo navegador
   @Bean
   public static BCryptPasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   @Autowired
   private SSUserDetailsService userDetailsService;

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetailsService userDetailsServiceBean() throws Exception{
      return new SSUserDetailsService(userRepository);
   }

   //Caso não ocorra a autenticação sera rediricionado para a tela de login
   @Override
   protected void configure(HttpSecurity http) throws Exception{
      http.authorizeRequests()
               .antMatchers("/", "/h2-console/**").permitAll()
               .antMatchers("/admin").access("hasAuthority('ADMIN')")
               .antMatchers("/login").permitAll()
               .antMatchers("/secure").authenticated()
               .and()
               .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               .logoutSuccessUrl("/login").permitAll()
               .and()
               .httpBasic();
         
      http.csrf().disable();
      http.headers().frameOptions().disable();

      
   }

   //Guarda em memoria para realizar a autenticação
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
      // auth.inMemoryAuthentication()
      //    .withUser("raul").password(passwordEncoder().encode("12345")).authorities("ADMIN")
      //    .and()
      //    .withUser("user")
      //    .password(passwordEncoder().encode("password")).authorities("USER");

      auth.userDetailsService(userDetailsServiceBean())
            .passwordEncoder(passwordEncoder());

   }

   
}
