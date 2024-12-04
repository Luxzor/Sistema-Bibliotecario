// src/main/java/mycompany/SpringPruebaMVC/model/services/LendingService.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.Lending;
import java.util.List;

public interface LendingService {
    List<Lending> findAllLendings();
    Lending findLendingById(Integer id);
    Lending saveLending(Lending lending);
    void deleteLending(Integer id);
    
    // Método actualizado para búsqueda filtrada
    List<Lending> searchPendingLendingsByUser(String searchTerm);
    
    void processReturn(Integer lendingId);
}
