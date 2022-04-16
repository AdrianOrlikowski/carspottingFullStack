package pl.orlikowski.carspottingBack.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.orlikowski.carspottingBack.repository.AppUser;
import pl.orlikowski.carspottingBack.service.UserService;

@RestController
@RequestMapping(path="users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @CrossOrigin
    @PostMapping(path = "/register")
    public AppUser registerUser(@RequestParam("username") String appUserUsername,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {

        AppUser newUser = new AppUser(appUserUsername, email, bCryptPasswordEncoder.encode(password));
        AppUser retUser = userService.addUser(newUser);
        return retUser;
    }
}
