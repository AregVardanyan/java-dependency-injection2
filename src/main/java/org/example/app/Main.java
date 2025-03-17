package org.example.app;

import org.example.infrastructure.Application;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Env;


public class Main {


    public static void main(String[] args) {
        ApplicationContext context = Application.run("org.example");

        UserRegistrationService registrationService = context.getObject(UserRegistrationService.class);

        registrationService.register(
                new User(
                        "Areg",
                        "a@a",
                        "password123"
                )
        );

        // First call: will execute the method and store the result in cache
        System.out.println(registrationService.expensiveMethod("test"));

        // Second call with the same argument: will return cached result
        System.out.println(registrationService.expensiveMethod("test"));

        // Third call with a different argument: will execute the method again
        System.out.println(registrationService.expensiveMethod("new_test"));
    }
}