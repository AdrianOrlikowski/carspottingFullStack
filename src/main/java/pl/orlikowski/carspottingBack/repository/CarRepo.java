package pl.orlikowski.carspottingBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

    @Transactional
    void deleteById(Long id);

    Optional<Car> findFirst1ByMakeIgnoreCaseAndModelIgnoreCase(String make, String model);
}
