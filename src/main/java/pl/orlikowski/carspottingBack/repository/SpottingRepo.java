package pl.orlikowski.carspottingBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SpottingRepo extends JpaRepository<Spotting, Long> {

    @Transactional
    void deleteById(Long id);
}
