package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String principal() {
        return "principal"; // Cambiar a minúsculas si la plantilla es 'principal.html'
    }
}
