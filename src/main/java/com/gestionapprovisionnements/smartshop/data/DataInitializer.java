//package com.gestionapprovisionnements.smartshop.data;
//
//import com.gestionapprovisionnements.smartshop.entity.User;
//import com.gestionapprovisionnements.smartshop.entity.enums.Role;
//import com.gestionapprovisionnements.smartshop.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        if (userRepository.findByUsername("admin").isEmpty()) {
//            User admin = User.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("admin123")) // mot de passe par défaut
//                    .role(Role.ADMIN)
//                    .build();
//
//            userRepository.save(admin);
//            System.out.println("ADMIN created : admin / admin123");
//        }
//    }
//}
//
