package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.User;
import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserById(Integer id);
    User saveUser(User user);
    void deleteUser(Integer id);
    
    // Nuevo método para buscar usuarios por nombre
    List<User> findUsersByNameContaining(String name);
    
     /**
     * Elimina múltiples usuarios por sus IDs.
     * @param userIds Lista de IDs de usuarios a eliminar.
     */
    void bulkDeleteUsers(List<Integer> userIds);
}
