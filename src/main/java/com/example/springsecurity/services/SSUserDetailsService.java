package com.example.springsecurity.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {
   
   private UserRepository userRepostory;

   public SSUserDetailsService(UserRepository userRepository) {
      this.userRepostory = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      try {
         User user = userRepostory.findByUsername(username);
         if(user == null){
            return null;
         }

         return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));

      } catch (Exception e) {
         throw new UsernameNotFoundException("User not found!");
      }
   }

   private Set<GrantedAuthority> getAuthorities(User user){
      Set<GrantedAuthority> authorities = new HashSet<>();
      for(Role role: user.getRoles()){
         GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
         authorities.add(grantedAuthority);
      }
      return authorities;
   }
}
