package com.natusvincere.mindglow;

import com.natusvincere.mindglow.auth.AuthenticationService;
import com.natusvincere.mindglow.auth.RegisterRequest;
import com.natusvincere.mindglow.user.EnableUserRequest;
import com.natusvincere.mindglow.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.natusvincere.mindglow.user.Role.ADMIN;
import static com.natusvincere.mindglow.user.Role.TEACHER;

@SpringBootApplication
public class MindBlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindBlowApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            UserService userService
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
            userService.enableUser(new EnableUserRequest(1, admin.getFirstname(), admin.getLastname(), ADMIN));
            for (int i = 0; i < 100; i++) {
                var teacher = RegisterRequest.builder()
                        .firstname("Teacher" + i)
                        .lastname("Teacher" + i)
                        .email("teacher@mail.com" + i)
                        .password("password" + i)
                        .role(TEACHER)
                        .build();
                System.out.println("Teacher token: " + service.register(teacher).getAccessToken());
            }
        };
    }

}
