// src/main/java/mycompany/SpringPruebaMVC/model/services/LendingsDetailedServiceImpl.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.LendingsDetailed;
import mycompany.SpringPruebaMVC.model.repositories.LendingsDetailedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendingsDetailedServiceImpl implements LendingsDetailedService {

    private final LendingsDetailedRepository lendingsDetailedRepository;

    @Autowired
    public LendingsDetailedServiceImpl(LendingsDetailedRepository lendingsDetailedRepository) {
        this.lendingsDetailedRepository = lendingsDetailedRepository;
    }

    @Override
    public List<LendingsDetailed> findPendingLendingsByUserName(String userName) {
        return lendingsDetailedRepository.findByUserNameAndDateReturnIsNull(userName);
    }
}
