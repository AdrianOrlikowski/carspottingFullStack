package pl.orlikowski.carspottingBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.orlikowski.carspottingBack.businessClasses.Spotting;

import java.util.List;

@Repository
public interface SpottingRepo extends JpaRepository<Spotting, Long> {

    List<Spotting> findAllByCarMakeIgnoreCaseAndCarModelIgnoreCase(String carMake, String carModel);

    List<Spotting> findAllByCarMakeIgnoreCase(String carMake);

    List<Spotting> findAllByAppUserUsername(String appUserUsername);

    @Transactional
    void deleteById(Long id);
}
