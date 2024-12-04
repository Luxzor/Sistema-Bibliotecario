// src/main/java/mycompany/SpringPruebaMVC/model/services/LendingServiceImpl.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.Lending;
import mycompany.SpringPruebaMVC.model.entities.Book;
import mycompany.SpringPruebaMVC.model.repositories.LendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LendingServiceImpl implements LendingService {

    private final LendingRepository lendingRepository;
    private final BookService bookService;

    @Autowired
    public LendingServiceImpl(LendingRepository lendingRepository, BookService bookService) {
        this.lendingRepository = lendingRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Lending> findAllLendings() {
        return lendingRepository.findAll();
    }

    @Override
    public Lending findLendingById(Integer id) {
        Optional<Lending> optionalLending = lendingRepository.findById(id);
        return optionalLending.orElse(null);
    }

    @Override
    @Transactional
    public Lending saveLending(Lending lending) {
        return lendingRepository.save(lending);
    }

    @Override
    public void deleteLending(Integer id) {
        lendingRepository.deleteById(id);
    }

    @Override
    public List<Lending> searchPendingLendingsByUser(String searchTerm) {
        return lendingRepository.searchPendingLendingsByUser(searchTerm);
    }

    @Override
    @Transactional
    public void processReturn(Integer lendingId) {
        Optional<Lending> optionalLending = lendingRepository.findById(lendingId);
        if (optionalLending.isPresent()) {
            Lending lending = optionalLending.get();
            if (lending.getDateReturn() == null) {
                lending.setDateReturn(java.time.LocalDate.now());
                lendingRepository.save(lending);

                // Actualizar la disponibilidad del libro
                Book book = lending.getBook();
                book.setAvailable(book.getAvailable() + 1);
                bookService.saveBook(book);
            } else {
                throw new IllegalStateException("El préstamo ya ha sido devuelto.");
            }
        } else {
            throw new IllegalArgumentException("Préstamo no encontrado.");
        }
    }
}
