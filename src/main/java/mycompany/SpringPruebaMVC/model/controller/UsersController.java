package Controller;

import Model_Entities.User;
import Model_Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired // Inyección de dependencias
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    // Mostrar lista de usuarios con búsqueda
    @GetMapping
    public String listUsers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<User> users;
        if (search != null && !search.isEmpty()) {
            users = userService.findUsersByNameContaining(search);
            model.addAttribute("search", search);
        } else {
            users = userService.findAllUsers();
        }
        model.addAttribute("users", users);
        return "users/list";
    }

    // Mostrar formulario para agregar un nuevo usuario
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/form";
    }

    // Guardar un nuevo usuario o actualizar uno existente
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // Mostrar formulario para editar un usuario existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        if (user == null) {
            // Maneja el caso donde el usuario no existe
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/form";
    }

    // Eliminar un usuario
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    
    @PostMapping("/bulkDelete")
    public String bulkDeleteUsers(@RequestParam(value = "selectedUsers", required = false) List<Integer> selectedUsers,
                                  RedirectAttributes redirectAttributes) {
        if (selectedUsers == null || selectedUsers.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No se seleccionaron usuarios para eliminar.");
            logger.warn("Intento de eliminación masiva sin usuarios seleccionados.");
            return "redirect:/users";
        }

        try {
            userService.bulkDeleteUsers(selectedUsers);
            redirectAttributes.addFlashAttribute("message", "Usuarios eliminados exitosamente.");
            logger.info("Eliminados usuarios con IDs: {}", selectedUsers);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar los usuarios: " + e.getMessage());
            logger.error("Error al eliminar usuarios con IDs {}: {}", selectedUsers, e.getMessage());
        }

        return "redirect:/users";
    }
}
