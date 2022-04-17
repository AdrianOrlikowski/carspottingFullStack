package pl.orlikowski.carspottingBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SpottingRepo extends JpaRepository<Spotting, Long> {

    List<Spotting> findAllByCarMakeAndCarModel(String carMake, String carModel);

    List<Spotting> findAllByCarMake(String carMake);

    @Transactional
    void deleteById(Long id);
}
