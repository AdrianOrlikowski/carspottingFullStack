package pl.orlikowski.carspottingBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.businessClasses.Car;
import pl.orlikowski.carspottingBack.businessClasses.Spotting;
import pl.orlikowski.carspottingBack.globals.Globals;
import pl.orlikowski.carspottingBack.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final SpottingRepo spottingRepo;
    private final TokenGenerator tokenGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,
                       CarRepo carRepo,
                       SpottingRepo spottingRepo,
                       TokenGenerator tokenGenerator,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.tokenGenerator = tokenGenerator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.carRepo = carRepo;
        this.spottingRepo = spottingRepo;
    }

    public List<AppUser> getUsers() { return userRepo.findAll(); }

    public AppUser getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException(
                "user with id " + userId + " does not exist"));
    }

    public AppUser getUserByEmail(String email) {
        return userRepo.findUserByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(
                "user with  " + email + " address does not exist"));
    }

    public AppUser addUser(String appUserUsername, String email, String password) {
        if(userRepo.findUserByEmailIgnoreCase(email).isPresent()) {
            throw new RuntimeException("There is already an account associated with this email.");
        } else if(userRepo.findUserByUsernameIgnoreCase(appUserUsername).isPresent()) {
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
        Optional<AppUser> userOpt = userRepo.findUserByEmailIgnoreCase(email);
        if(userOpt.isEmpty()){
            throw new RuntimeException("user with " + email + " address does not exist");
        }
        AppUser user = userOpt.get();
        user.setActivationToken(tokenGenerator.generateToken(Globals.tokenSize));
        userRepo.save(user);
        return user;
    }

    public String resetPassword(String email) {
        Optional<AppUser> userOpt = userRepo.findUserByEmailIgnoreCase(email);
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

    public AppUser deleteUser(String username) {
        Optional<AppUser> userOpt = userRepo.findUserByUsernameIgnoreCase(username);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("user with username:" + username + " does not exist");
        }
        AppUser userDeleted = userOpt.get();
        //getting all the cars spotted by the user we want to delete
        List<Car> cars = spottingRepo.findAllByAppUserUsername(username)
                .stream().map(Spotting::getCar).toList();

        userRepo.deleteAppUserByUsername(username);

        //After deleting user all his spottings are deleted automatically.
        //After this we check for cars with no spottings and delete them
        for(Car car: cars) {
            if (car.getSpottings().isEmpty()) carRepo.delete(car);
        }
        return userDeleted;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with " + username + " does not exist"));
    }
}
