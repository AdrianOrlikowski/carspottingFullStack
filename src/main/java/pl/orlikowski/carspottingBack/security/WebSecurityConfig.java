package pl.orlikowski.carspottingBack.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.orlikowski.carspottingBack.services.UserService;
import pl.orlikowski.carspottingBack.globals.Globals;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder ) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/*");
//    }

    //Na razie puszczamy wszystko
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //Żeby post działał

        if (Globals.secType == SecType.NO_SECURITY){
            http.authorizeRequests().anyRequest().permitAll();
        } else if (Globals.secType == SecType.HTTP_BASIC) {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/search").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/users/register").permitAll()
                    .antMatchers("/users/activate").permitAll()
                    .antMatchers("/css/*", "/js/*").permitAll()
                    .antMatchers("/data/spots").permitAll()
                    .antMatchers("/data/search").permitAll()
                    .anyRequest().authenticated().and().httpBasic();
        } else if (Globals.secType == SecType.FORM_LOGIN) {
            http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/activate").permitAll()
                .antMatchers("/css/*", "/js/*").permitAll()
                .antMatchers("/data/spots").permitAll()
                .antMatchers("/data/search").permitAll()
                .anyRequest().authenticated().and().formLogin()
                    .loginPage("/login");

        }

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

}
