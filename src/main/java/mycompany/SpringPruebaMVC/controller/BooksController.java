package Controller;

import Model_Entities.Book;
import Model_Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
  private static final Logger logger = LoggerFactory.getLogger(BooksController.class);
    @Autowired 
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    // Mostrar lista de libros con búsqueda
    @GetMapping
    public String listBooks(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Book> books;   
        if (search != null && !search.isEmpty()) {
            books = bookService.findBooksByTitleContaining(search);
            model.addAttribute("search", search);
        } else {
            books = bookService.findAllBooks();
        }
        model.addAttribute("books", books);
        return "books/list";
    }

    // Mostrar formulario para agregar un nuevo libro
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "books/form";
    }

    // Guardar un nuevo libro o actualizar uno existente
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Mostrar formulario para editar un libro existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/form";
    }

    // Eliminar un libro
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    
    @PostMapping("/bulkDelete")
    public String bulkDeleteBooks(@RequestParam(value = "selectedBooks", required = false) List<Integer> selectedBooks,
                                  RedirectAttributes redirectAttributes) {
        if (selectedBooks == null || selectedBooks.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No se seleccionaron libros para eliminar.");
            logger.warn("Intento de eliminación masiva sin libros seleccionados.");
            return "redirect:/books";
        }

        try {
            bookService.bulkDeleteBooks(selectedBooks);
            redirectAttributes.addFlashAttribute("message", "Libros eliminados exitosamente.");
            logger.info("Eliminados libros con IDs: {}", selectedBooks);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar los libros: " + e.getMessage());
            logger.error("Error al eliminar libros con IDs {}: {}", selectedBooks, e.getMessage());
        }

        return "redirect:/books";
    }
}
