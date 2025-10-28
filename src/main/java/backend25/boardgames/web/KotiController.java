package backend25.boardgames.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KotiController {

    @GetMapping({"/", "/koti"})
    public String koti(Model model) {
        model.addAttribute("message", "Tervetuloa lautapelikokoelman pariin!");
        model.addAttribute("description", "Täältä löydät lautapelejä moneen makuun.");
        return "koti";
    }
}