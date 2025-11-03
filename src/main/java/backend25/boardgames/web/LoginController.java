package backend25.boardgames.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")       // Käsittelee GET-pyynnön polkuun /login
    public String login() {
        return "login"; // Palauttaa näkymän login.html (Thymeleaf). Tämä näkymä sisältää kirjautumislomakkeen
    }
}
