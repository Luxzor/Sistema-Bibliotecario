// src/main/java/mycompany/SpringPruebaMVC/model/services/UserServiceImpl.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.User;
import mycompany.SpringPruebaMVC.model.entities.ActionType;
import mycompany.SpringPruebaMVC.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReportEntryService reportEntryService;

    @Autowired // Inyección por constructor
    public UserServiceImpl(UserRepository userRepository, ReportEntryService reportEntryService) {
        this.userRepository = userRepository;
        this.reportEntryService = reportEntryService;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        // Crear reporte de nuevo usuario
        reportEntryService.createReport(
            ActionType.NEW_USER,
            "Se agregó el usuario: " + savedUser.getName(),
            savedUser.getId()
        );
        return savedUser;
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Verificar si el usuario tiene préstamos pendientes
            if (user.getLendings() != null && !user.getLendings().isEmpty()) {
                throw new IllegalStateException("El usuario '" + user.getName() + "' tiene préstamos pendientes y no puede ser eliminado.");
            }
            userRepository.deleteById(id);
            // Crear reporte de eliminación de usuario
            reportEntryService.createReport(
                ActionType.DELETE_USER,
                "Se eliminó el usuario: " + user.getName(),
                id
            );
        }
    }

    @Override
    public List<User> findUsersByNameContaining(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    @Transactional
    public void bulkDeleteUsers(List<Integer> userIds) {
        List<User> usersToDelete = userRepository.findAllById(userIds);
        if (usersToDelete.size() != userIds.size()) {
            throw new IllegalArgumentException("Algunos usuarios no fueron encontrados para eliminar.");
        }
        for (User user : usersToDelete) {
            if (user.getLendings() != null && !user.getLendings().isEmpty()) {
                throw new IllegalStateException("El usuario '" + user.getName() + "' tiene préstamos pendientes y no puede ser eliminado.");
            }
            // Crear reporte de eliminación de usuario
            reportEntryService.createReport(
                ActionType.DELETE_USER,
                "Se eliminó el usuario: " + user.getName(),
                user.getId()
            );
        }
        userRepository.deleteAll(usersToDelete);
    }
}
