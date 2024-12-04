package mycompany.SpringPruebaMVC.model.repositories;

import mycompany.SpringPruebaMVC.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Método de consulta personalizado para buscar por nombre
    List<User> findByNameContainingIgnoreCase(String name);
}
