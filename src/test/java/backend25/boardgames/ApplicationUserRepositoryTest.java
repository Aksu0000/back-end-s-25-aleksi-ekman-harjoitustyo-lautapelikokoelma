package backend25.boardgames;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import backend25.boardgames.domain.ApplicationUser;
import backend25.boardgames.domain.ApplicationUserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationUserRepositoryTest {

    @Autowired
    private ApplicationUserRepository userRepository;

    private ApplicationUser testUser;

    @BeforeEach
    public void setupTestData() {
        testUser = userRepository.findByUsername("testuser");
        if (testUser != null)
            userRepository.delete(testUser);

        testUser = new ApplicationUser("Test", "User", "testuser", "$2a$10$hash", "USER");
        userRepository.save(testUser);
    }

    @AfterEach
    public void cleanUpTestData() {
        ApplicationUser user = userRepository.findByUsername("testuser");
        if (user != null)
            userRepository.delete(user);
    }

    @Test
    public void testCreateSearchDeleteUser() {
        ApplicationUser foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isNotNull();

        userRepository.delete(foundUser);
        foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isNull();
    }
}
