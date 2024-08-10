package com.ugurhalil.web.flux.security.bootstrap;

import com.ugurhalil.web.flux.security.model.RoleDto;
import com.ugurhalil.web.flux.security.model.UserCreateRequest;
import com.ugurhalil.web.flux.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        loadBeerData();
    }

    private void loadBeerData() {
        if (false) {
            userService.saveRole(RoleDto.builder()
                            .name("ADMIN")
                            .build())
                    .subscribe();

            userService.saveRole(RoleDto.builder()
                            .name("USER")
                            .build())
                    .subscribe();

            userService.save(createUser(
                            "halilugur",
                            "mr.halilugur@gmail.com"))
                    .subscribe();
        }
    }

    private UserCreateRequest createUser(String username, String email) {
        return new UserCreateRequest(username, email, "123");
    }
}