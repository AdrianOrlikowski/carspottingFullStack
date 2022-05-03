package pl.orlikowski.carspottingBack.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.mailing.MailingService;
import pl.orlikowski.carspottingBack.services.UserService;


//Controller handling user registration and activation.
@RestController
@RequestMapping(path="users")
public class UserController {

    private final UserService userService;
    private final MailingService mailingService;

    @Autowired
    public UserController(UserService userService, MailingService mailingService) {
        this.userService = userService;
        this.mailingService = mailingService;
    }

    @CrossOrigin
    @PostMapping(path = "/register")
    public String registerUser(@RequestParam("username") String appUserUsername,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {

        AppUser retUser = userService.addUser(appUserUsername, email, password);
        mailingService.sendActivationMail(retUser.getEmail(), retUser.getActivationToken());
        return "Account for " + retUser.getUsername() + " has been created."
                + "Activation link sent to " + retUser.getEmail();
    }

    @GetMapping(path="/activate")
    public String activateUser(@RequestParam("token") String token) {
        AppUser user = userService.activateUser(token);
        return "User account for " + user.getUsername() + " has been activated";
    }

    @CrossOrigin
    @PostMapping(path="/resendtoken")
    public String resendToken(@RequestParam("email") String email) {
        AppUser user =  userService.updateToken(email);
        mailingService.sendActivationMail(user.getEmail(), user.getActivationToken());
        return "New activation token has been sent to: " + email;
    }

    @CrossOrigin
    @PostMapping(path="/resetpassword")
    public String resetPassword(@RequestParam("email") String email) {
        String newPass = userService.resetPassword(email);
        mailingService.sendResetPassword(email, newPass);
        return "Your password has been reset. New password sent to: " + email;
    }

    @CrossOrigin
    @DeleteMapping(path="/deleteaccount")
    public String deleteUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
       AppUser deletedUser = userService.deleteUser(username);
       return "User " + username + " deleted.";
    }


}
