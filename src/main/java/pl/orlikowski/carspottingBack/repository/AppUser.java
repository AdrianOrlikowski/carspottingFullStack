package pl.orlikowski.carspottingBack.repository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
            nullable = false)
    private String username;
    @Column(
            name = "email",
            nullable = false)
    private String email;

    private String password;
    @OneToMany(
            mappedBy = "appUser",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Spotting> spottings = new ArrayList<>();

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AppUser() {}

    public Long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Spotting> getSpottings() { return spottings; }

    public void setSpottings(List<Spotting> spottings) {
        this.spottings = spottings;
    }

    /////////////////////////////////////////////////////////////////////////
    //Adding spotings
    public void addSpot(Car car) {
        Spotting spot = new Spotting(this, car, LocalDateTime.now());
        this.spottings.add(spot);
    }
    //Removing spottings
    public void removePost(Spotting spot) {
        this.spottings.remove(spot);
    }

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

    //Not implementing these security features
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }


}
