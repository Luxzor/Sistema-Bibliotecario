package mycompany.SpringPruebaMVC.model.repositories;

import mycompany.SpringPruebaMVC.model.entities.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendingRepository extends JpaRepository<Lending, Integer> {

    // Método de búsqueda flexible por nombre y apellidos, filtrando préstamos pendientes
    @Query("SELECT l FROM Lending l WHERE "
            + "(LOWER(l.user.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(l.user.lastNameP) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(l.user.lastNameM) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND "
            + "l.dateReturn IS NULL")
    List<Lending> searchPendingLendingsByUser(@Param("searchTerm") String searchTerm);

}
