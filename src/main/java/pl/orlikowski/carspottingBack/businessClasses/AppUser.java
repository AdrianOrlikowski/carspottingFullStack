package pl.orlikowski.carspottingBack.businessClasses;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "AppUser")
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(
            name = "user_id",
            updatable = false)
    private Long userId;
    @Column(
            name = "username",
            nullable = false,
            unique = true)
    private String username;
    @Column(
            name = "email",
            nullable = false,
            unique = true)
    private String email;

    private String password;

    @Column(name="activation_token")
    private String activationToken;

    @Column(name="enabled")
    private boolean enabled;

    @OneToMany(
            mappedBy = "appUser",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Spotting> spottings = new ArrayList<>();

    public AppUser(String username, String email, String password, boolean enabled, String activationToken) {
        this.username = username.toLowerCase();
        this.email = email.toLowerCase();
        this.password = password;
        this.enabled = enabled;
        this.activationToken = activationToken;
    }

    public AppUser() {}

    public Long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setPassword(String password) { this.password = password; }

    public String getActivationToken() { return activationToken; }

    public void setActivationToken(String activationToken) { this.activationToken = activationToken; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<Spotting> getSpottings() { return spottings; }

    public void setSpottings(List<Spotting> spottings) {
        this.spottings = spottings;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", activationToken='" + activationToken + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    /////////////////////////////////////////////////////////////////////////
    /*
    //Adding spotings
    public void addSpot(Car car) {
        Spotting spot = new Spotting(this, car, LocalDateTime.now());
        this.spottings.add(spot);
    }
    //Removing spottings
    public void removeSpot(Spotting spot) {
        this.spottings.remove(spot);
    }
    */
    /////////////////////////////////////////////////////////////////////////
    //Security stuff
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        return Collections.singletonList(authority);
    }
    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isEnabled() { return enabled; }

    //Not implementing these security features
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }



}
