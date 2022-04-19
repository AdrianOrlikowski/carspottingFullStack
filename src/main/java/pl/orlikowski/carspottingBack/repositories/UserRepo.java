package pl.orlikowski.carspottingBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmail(String email);

    Optional<AppUser> findUserByActivationToken(String activationToken);

    Optional<AppUser> findUserByUsername(String username);



}
