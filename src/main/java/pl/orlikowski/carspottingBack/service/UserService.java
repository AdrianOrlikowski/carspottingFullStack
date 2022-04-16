package pl.orlikowski.carspottingBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.repository.*;

import java.util.List;

@Service
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<AppUser> getUsers() { return userRepo.findAll(); }

    public AppUser getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new IllegalStateException(
                "user with id " + userId + " does not exist"));
    }

    public AppUser getUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new IllegalStateException(
                "user with id " + email + " does not exist"));
    }

    public AppUser AddUser(AppUser newUser) {
        if(userRepo.findUserByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalStateException("There is already an account associated with this email.");
        } else if(userRepo.findUserByEmail(newUser.getUsername()).isPresent()) {
            throw new IllegalStateException("This username is already taken");
        } else {
            userRepo.save(newUser);
            return newUser;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with " + username + " does not exist"));
    }
}
