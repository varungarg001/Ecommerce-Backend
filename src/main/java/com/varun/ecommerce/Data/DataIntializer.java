package com.varun.ecommerce.Data;

import com.varun.ecommerce.model.User;
import com.varun.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataIntializer implements ApplicationListener<ApplicationEvent> {
    private final UserRepo userRepo;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        createDefaultIfNotExists();
    }

    private void createDefaultIfNotExists(){
        for(int i=1;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";
            if(userRepo.existsByEmail(defaultEmail)){
                continue;
            }
            User user=new User();
            user.setFirstName("The user");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setPassword("12345");
            userRepo.save(user);
            System.out.println("User created successfully"+i);
        }
    }
}
