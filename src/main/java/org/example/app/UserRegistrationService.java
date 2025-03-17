package org.example.app;


import org.example.infrastructure.annotation.*;
import org.example.infrastructure.enums.ScopeType;

@Scope(value = ScopeType.PROTOTYPE)
@Component
public class UserRegistrationService {

    @Inject
    private UserRepository userRepository;

    @Inject
    @Qualifier(DefaultEmailSender2.class)
    private EmailSender emailSender;

    public void register(User user) {
        User existingUser = userRepository.getUser(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistsException(
                    "User is already registered. Username: " + user.getUsername()
            );
        }

        userRepository.save(user);

        emailSender.send(
                user.getEmail(),
                "Account confirmation",
                "Please confirm your newly created account"
        );
    }

    @PostConstruct
    public void initialize() {
        System.out.println("PostConstruct: Dependencies initialized");
    }

    @Cacheable
    public String expensiveMethod(@CacheKey String param) {
        System.out.println("Executing operation: ");
        return "Result for " + param;
    }
}
