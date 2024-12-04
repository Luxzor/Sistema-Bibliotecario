// src/main/java/mycompany/SpringPruebaMVC/model/services/BookServiceImpl.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.Book;
import mycompany.SpringPruebaMVC.model.entities.ActionType;
import mycompany.SpringPruebaMVC.model.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ReportEntryService reportEntryService;

    @Autowired // Inyección por constructor
    public BookServiceImpl(BookRepository bookRepository, ReportEntryService reportEntryService) {
        this.bookRepository = bookRepository;
        this.reportEntryService = reportEntryService;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        // Crear reporte de nuevo libro
        reportEntryService.createReport(
            ActionType.NEW_BOOK,
            "Se agregó el libro: " + savedBook.getTitle(),
            savedBook.getId()
        );
        return savedBook;
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            // Verificar si el libro tiene préstamos pendientes
            if (book.getLendings() != null && !book.getLendings().isEmpty()) {
                throw new IllegalStateException("El libro '" + book.getTitle() + "' tiene préstamos pendientes y no puede ser eliminado.");
            }
            bookRepository.deleteById(id);
            // Crear reporte de eliminación de libro
            reportEntryService.createReport(
                ActionType.DELETE_BOOK,
                "Se eliminó el libro: " + book.getTitle(),
                id
            );
        }
    }

    @Override
    public List<Book> findBooksByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    @Override
    @Transactional
    public void bulkDeleteBooks(List<Integer> bookIds) {
        List<Book> booksToDelete = bookRepository.findAllById(bookIds);
        if (booksToDelete.size() != bookIds.size()) {
            throw new IllegalArgumentException("Algunos libros no fueron encontrados para eliminar.");
        }
        for (Book book : booksToDelete) {
            if (book.getLendings() != null && !book.getLendings().isEmpty()) {
                throw new IllegalStateException("El libro '" + book.getTitle() + "' tiene préstamos pendientes y no puede ser eliminado.");
            }
            // Crear reporte de eliminación de libro
            reportEntryService.createReport(
                ActionType.DELETE_BOOK,
                "Se eliminó el libro: " + book.getTitle(),
                book.getId()
            );
        }
        bookRepository.deleteAll(booksToDelete);
    }
}
