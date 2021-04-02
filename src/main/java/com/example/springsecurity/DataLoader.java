package com.example.springsecurity;

import java.util.Arrays;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

   @Autowired
   UserRepository userRepository;

   @Autowired
   RoleRepository roleRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEnconder;


   @Override
   public void run(String... args) throws Exception {
      roleRepository.save(new Role("USER"));
      roleRepository.save(new Role("ADMIN"));

      Role adminRole = roleRepository.findByRole("ADMIN");
      Role userRole = roleRepository.findByRole("USER");

      User user = new User("admin@email.com", passwordEnconder.encode("password"), "Admin", "Super", true, "admin");
      user.setRoles(Arrays.asList(adminRole));
      userRepository.save(user);

      user = new User("user@email.com", passwordEnconder.encode("password"), "User", "Super", true, "user");
      user.setRoles(Arrays.asList(userRole));
      userRepository.save(user);
   }
}
