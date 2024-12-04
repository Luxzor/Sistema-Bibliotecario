package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.Book;
import java.util.List;

public interface BookService {
    List<Book> findAllBooks();
    Book findBookById(Integer id);
    Book saveBook(Book book);
    void deleteBook(Integer id);
    
    // Nuevo método para buscar libros por título
    List<Book> findBooksByTitleContaining(String title);
    
    /**
     * Elimina múltiples libros por sus IDs.
     * @param bookIds Lista de IDs de libros a eliminar.
     */
    void bulkDeleteBooks(List<Integer> bookIds);
}
