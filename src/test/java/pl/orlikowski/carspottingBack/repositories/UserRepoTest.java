package pl.orlikowski.carspottingBack.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {
    @Autowired
    private UserRepo underTest;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUser testUser;

    @BeforeEach
    void prepareTestData() {
        String username = "testUser";
        String email = "testuser@gmail.com";
        String token = "testToken";
        String password = bCryptPasswordEncoder.encode("testPassword");
        boolean enabled = true;

        testUser = new AppUser(username,email, password, enabled, token);
        underTest.save(testUser);
    }

    @AfterEach
    void clearTestData() {
        underTest.deleteAll();
    }

    @Test
    void findUserByEmail_Exists() {
        //given
        String email = testUser.getEmail();

        //when
        AppUser actual = underTest.findUserByEmailIgnoreCase(email).get();

        //then
        assertEquals(testUser.getUserId(),actual.getUserId());
    }

    @Test
    void findUserByEmail_NotExists() {
        //given
        String email = "thisemailisnotindatabase@gmail.com";

        //when
        Optional<AppUser> actual = underTest.findUserByEmailIgnoreCase(email);

        //then
        assertEquals(Optional.empty(),actual);
    }

    @Test
    void findUserByEmail_DifferentCase() {
        //given
        String email = testUser.getEmail().toUpperCase();

        //when
        AppUser actual = underTest.findUserByEmailIgnoreCase(email).get();

        //then
        assertEquals(testUser.getUserId(),actual.getUserId());
    }

    @Test
    void addEmailAlreadyInDatabase() {
        Exception e = new Exception();
        try {
            String username = "someusername";
            String email = testUser.getEmail();
            String token = "testToken";
            String password = bCryptPasswordEncoder.encode("testPassword");
            boolean enabled = true;

            AppUser newUser = new AppUser(username, email, password, enabled, token);
            underTest.save(newUser);

        } catch (Exception exception) {
            e = exception;
        } finally {
            assertTrue(e instanceof DataIntegrityViolationException);
        }
    }


    @Test
    void addUsernameAlreadyInDatabase() {
        Exception e = new Exception();
        try {
            String username = testUser.getUsername();
            String email = "someemail@gmail.com";
            String token = "testToken";
            String password = bCryptPasswordEncoder.encode("testPassword");
            boolean enabled = true;

            AppUser newUser = new AppUser(username, email, password, enabled, token);
            underTest.save(newUser);

        } catch (Exception exception) {
            e = exception;
        } finally {
            assertTrue(e instanceof DataIntegrityViolationException);
        }

    }
}
