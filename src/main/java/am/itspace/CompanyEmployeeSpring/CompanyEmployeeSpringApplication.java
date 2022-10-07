package am.itspace.CompanyEmployeeSpring;

import am.itspace.CompanyEmployeeSpring.entity.Role;
import am.itspace.CompanyEmployeeSpring.entity.User;
import am.itspace.CompanyEmployeeSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class CompanyEmployeeSpringApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(CompanyEmployeeSpringApplication.class, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> byEmail = userRepository.findByEmail("admin@mail.com");
        if (byEmail.isEmpty()) {
            userRepository.save(User.builder()
                    .name("admin")
                    .surname("admin")
                    .email("admin@mail.com")
                    .password(passwordEncoder().encode("admin"))
                    .role(Role.MANAGER)
                    .build());
        }
    }
}
