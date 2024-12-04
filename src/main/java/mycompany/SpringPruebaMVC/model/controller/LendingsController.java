package Controller;

import Model_Entities.Lending;
import Model_Entities.Book;
import Model_Entities.User;
import Model_Services.LendingService;
import Model_Services.BookService;
import Model_Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lendings")
public class LendingsController {

    private final LendingService lendingService;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public LendingsController(LendingService lendingService, BookService bookService, UserService userService) {
        this.lendingService = lendingService;
        this.bookService = bookService;
        this.userService = userService;
    }

    // Mostrar lista de préstamos
    @GetMapping
    public String listLendings(Model model) {
        List<Lending> lendings = lendingService.findAllLendings();
        model.addAttribute("lendings", lendings);
        return "lendings/list"; // Asegúrate de tener esta vista
    }

    // Mostrar formulario para agregar un nuevo préstamo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Lending lending = new Lending();
        model.addAttribute("lending", lending);
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("users", userService.findAllUsers());
        return "lendings/form"; // Asegúrate de tener esta vista
    }

    // Guardar un nuevo préstamo o actualizar uno existente
    @PostMapping("/save")
    public String saveLending(@ModelAttribute("lending") Lending lending, RedirectAttributes redirectAttributes) {
        try {
            // Validar y asignar el libro
            Book book = bookService.findBookById(lending.getBook().getId());
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "El libro seleccionado no existe.");
                return "redirect:/lendings/new";
            }

            // Validar y asignar el usuario
            User user = userService.findUserById(lending.getUser().getId());
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "El usuario seleccionado no existe.");
                return "redirect:/lendings/new";
            }

            lending.setBook(book);
            lending.setUser(user);
            lendingService.saveLending(lending);
            redirectAttributes.addFlashAttribute("message", "Préstamo guardado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el préstamo: " + e.getMessage());
            return "redirect:/lendings/new";
        }
        return "redirect:/lendings";
    }

    // Eliminar un préstamo
    @GetMapping("/delete/{id}")
    public String deleteLending(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            lendingService.deleteLending(id);
            redirectAttributes.addFlashAttribute("message", "Préstamo eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el préstamo: " + e.getMessage());
        }
        return "redirect:/lendings";
    }

    // Mostrar formulario para editar un préstamo
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Lending lending = lendingService.findLendingById(id);
        if (lending == null) {
            return "redirect:/lendings";
        }
        model.addAttribute("lending", lending);
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("users", userService.findAllUsers());
        return "lendings/form";
    }
}
