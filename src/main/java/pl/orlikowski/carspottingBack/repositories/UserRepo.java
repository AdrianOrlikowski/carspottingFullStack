package pl.orlikowski.carspottingBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmailIgnoreCase(String email);

    Optional<AppUser> findUserByActivationToken(String activationToken);

    Optional<AppUser> findUserByUsernameIgnoreCase(String username);

    @Transactional
    void deleteAppUserByUsername(String username);



}
