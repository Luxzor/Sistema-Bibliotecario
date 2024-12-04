package mycompany.SpringPruebaMVC.model.repositories;

import mycompany.SpringPruebaMVC.model.entities.LendingsDetailed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LendingsDetailedRepository extends JpaRepository<LendingsDetailed, Integer> {
    List<LendingsDetailed> findByUserNameAndDateReturnIsNull(String userName);
}
