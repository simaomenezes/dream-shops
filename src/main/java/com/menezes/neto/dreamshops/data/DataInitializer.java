package com.menezes.neto.dreamshops.data;

import com.menezes.neto.dreamshops.model.User;
import com.menezes.neto.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationEvent> {
    private final UserRepository userRepository;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        creatwDefaultUserIfNotExists();
    }

    private void creatwDefaultUserIfNotExists() {
        for (int i = 1; i<=5; i++){
            String defaultEmail = "user"+i+"gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }
}
