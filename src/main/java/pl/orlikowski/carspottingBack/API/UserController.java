package pl.orlikowski.carspottingBack.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.mailing.MailingService;
import pl.orlikowski.carspottingBack.mailing.TokenGenerator;
import pl.orlikowski.carspottingBack.services.UserService;
import pl.orlikowski.carspottingBack.globals.Globals;

@RestController
@RequestMapping(path="users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenGenerator tokenGenerator;
    private final MailingService mailingService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
                          TokenGenerator tokenGenerator, MailingService mailingService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.mailingService = mailingService;
    }

    @CrossOrigin
    @PostMapping(path = "/register")
    public AppUser registerUser(@RequestParam("username") String appUserUsername,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {

        String token = tokenGenerator.generateToken(Globals.tokenSize);

        AppUser newUser = new AppUser(appUserUsername, email,
                bCryptPasswordEncoder.encode(password), false, token);
        AppUser retUser = userService.addUser(newUser);
        mailingService.sendActivationMail(email, token);

        return retUser;
    }

    @GetMapping(path="/activate")
    public AppUser activateUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @PostMapping(path="/resendtoken")
    public AppUser resendToken(@RequestParam("email") String email) {
        AppUser user =  userService.updateToken(email);
        mailingService.sendActivationMail(user.getEmail(), user.getActivationToken());
        return user;
    }


}
