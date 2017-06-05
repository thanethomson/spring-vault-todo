package com.thanethomson.demos.todo.config.repolisteners;

import com.thanethomson.demos.todo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RepositoryEventHandler
public class UserEventHandler {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserEventHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        // ensure that we store a salted hash of the user's password as opposed to the raw password
        user.password = passwordEncoder.encode(user.password);
    }

    @HandleBeforeSave
    public void handleUserSave(User user) {
        user.password = passwordEncoder.encode(user.password);
    }

}
