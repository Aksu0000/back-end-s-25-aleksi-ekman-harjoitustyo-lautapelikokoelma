package backend25.boardgames.service;

import backend25.boardgames.domain.ApplicationUser;
import backend25.boardgames.domain.ApplicationUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ApplicationUserRepository applicationUserRepository;
    public UserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }
}