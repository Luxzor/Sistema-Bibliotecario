package mycompany.SpringPruebaMVC.model.repositories;

import mycompany.SpringPruebaMVC.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Método de consulta personalizado para buscar por título
    List<Book> findByTitleContainingIgnoreCase(String title);
}
