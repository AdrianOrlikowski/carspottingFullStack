package pl.orlikowski.carspottingBack.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.mailing.MailingService;
import pl.orlikowski.carspottingBack.mailing.TokenGenerator;
import pl.orlikowski.carspottingBack.services.UserService;
import pl.orlikowski.carspottingBack.globals.Globals;


//Controller handling user registration and activation.
//Currently returns user entity as JSON - to be integrated with front
@RestController
@RequestMapping(path="users")
public class UserController {

    private final UserService userService;
    private final MailingService mailingService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
                          TokenGenerator tokenGenerator, MailingService mailingService) {
        this.userService = userService;
        this.mailingService = mailingService;
    }

    @CrossOrigin
    @PostMapping(path = "/register")
    public AppUser registerUser(@RequestParam("username") String appUserUsername,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {

        AppUser retUser = userService.addUser(appUserUsername, email, password);
        mailingService.sendActivationMail(retUser.getEmail(), retUser.getActivationToken());
        return retUser;
    }

    @GetMapping(path="/activate")
    public AppUser activateUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @CrossOrigin
    @PostMapping(path="/resendtoken")
    public AppUser resendToken(@RequestParam("email") String email) {
        AppUser user =  userService.updateToken(email);
        mailingService.sendActivationMail(user.getEmail(), user.getActivationToken());
        return user;
    }


}
