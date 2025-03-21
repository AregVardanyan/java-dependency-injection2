package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Env;

import java.util.List;

@Component
public interface UserRepository {



    void save(User user);

    User getUser(String username);

    List<User> getAll();
}
