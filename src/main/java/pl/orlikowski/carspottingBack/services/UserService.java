package pl.orlikowski.carspottingBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.globals.Globals;
import pl.orlikowski.carspottingBack.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;
    private final TokenGenerator tokenGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,
                       TokenGenerator tokenGenerator,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.tokenGenerator = tokenGenerator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<AppUser> getUsers() { return userRepo.findAll(); }

    public AppUser getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException(
                "user with id " + userId + " does not exist"));
    }

    public AppUser getUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                "user with  " + email + " address does not exist"));
    }

    public AppUser addUser(String appUserUsername, String email, String password) {
        if(userRepo.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("There is already an account associated with this email.");
        } else if(userRepo.findUserByUsername(appUserUsername).isPresent()) {
            throw new RuntimeException("This username is already taken");
        }
        //generating activation token
        String token = tokenGenerator.generateToken(Globals.tokenSize);
        //creating new user
        AppUser newUser = new AppUser(appUserUsername, email,
                bCryptPasswordEncoder.encode(password), false, token);
        userRepo.save(newUser);
        return newUser;
    }

    public AppUser activateUser(String token) {
        Optional<AppUser> userOpt = userRepo.findUserByActivationToken(token);
        if(userOpt.isEmpty()){
            throw new RuntimeException("Incorrect activation token");
        }
        AppUser userToActivate = userOpt.get();
        userToActivate.setEnabled(true);
        userRepo.save(userToActivate);
        return userToActivate;
    }

    public AppUser updateToken(String email) {
        Optional<AppUser> userOpt = userRepo.findUserByEmail(email);
        if(userOpt.isEmpty()){
            throw new RuntimeException("user with " + email + " address does not exist");
        }
        AppUser user = userOpt.get();
        user.setActivationToken(tokenGenerator.generateToken(Globals.tokenSize));
        userRepo.save(user);
        return user;
    }

    public String resetPassword(String email) {
        Optional<AppUser> userOpt = userRepo.findUserByEmail(email);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("user with " + email + " address does not exist");
        }
        AppUser user = userOpt.get();
        //we use tokenGenerator to generate random password
        String newPass = tokenGenerator.generateToken(Globals.resetPassLength);
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepo.save(user);
        return newPass;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with " + username + " does not exist"));
    }
}
