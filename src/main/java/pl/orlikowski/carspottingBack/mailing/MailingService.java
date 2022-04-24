package pl.orlikowski.carspottingBack.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.globals.Globals;

@Service
public class MailingService {
    private JavaMailSenderImpl mailSender;

    @Autowired
    public MailingService(EmailConfiguration emailConfiguration) {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());
    }

    public void sendActivationMail(String email, String token){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(Globals.mailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject("Acount Activation");
        mailMessage.setText("Welcome to Carspotting Warsaw, " +
                "please paste the link below into your browser to activate account:\n" +
                Globals.activationLink + token);
        mailSender.send(mailMessage);
    }

    public void sendResetPassword(String email, String password){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(Globals.mailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject("Password reset");
        mailMessage.setText("Carspotting Warsaw: your password has been reset. " +
                "Your temporary password is:\n" + password);
        mailSender.send(mailMessage);
    }
}
