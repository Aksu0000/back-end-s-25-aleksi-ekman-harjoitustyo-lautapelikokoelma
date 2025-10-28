package backend25.boardgames.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import backend25.boardgames.domain.GenreRepository;
import jakarta.validation.Valid;

//import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class BoardgameController {

    private final BoardgameRepository repository;
    private final GenreRepository crepository;

	public BoardgameController(BoardgameRepository repository, GenreRepository crepository) {
		this.repository = repository;
        this.crepository = crepository;
	}

    @GetMapping(value= {"/boardgamelist"})
	public String boardgameList(Model model) {
		model.addAttribute("boardgames", repository.findAll());
		return "boardgamelist";
	}

    @GetMapping(value = "/add")
    public String addBoardgame(Model model) {
        model.addAttribute("boardgame", new Boardgame());
        model.addAttribute("genres", crepository.findAll());
        return "addboardgame";
    }

    @PostMapping(value = "/saveBoardgame")
    public String saveBoardgame(@Valid @ModelAttribute("boardgame") Boardgame boardgame,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", crepository.findAll());
            return "addboardgame";
        }
        repository.save(boardgame);
        return "redirect:/boardgamelist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/deleteBoardgame/{id}")
    public String deleteBoardgame(@PathVariable("id") Long boardgameId, Model model) {
        repository.deleteById(boardgameId);
        return "redirect:../boardgamelist";
    }

    @GetMapping(value = "/editBoardgame/{id}")
    public String editBoardgame(@PathVariable("id") Long boardgameId, Model model) {
        Boardgame boardgame = repository.findById(boardgameId).get(); // haetaan kirja reposta
        model.addAttribute("editBoardgame", boardgame); // lisätään malliobjekti "boardgame", jota lomake käyttää
        model.addAttribute("genres", crepository.findAll());
        return "editboardgamewithvalidation"; // näkymä editbook.html
    }

    @PostMapping("/saveEditedBoardgame")
    public String saveEditedBoardgame(@Valid @ModelAttribute("editBoardgame") Boardgame boardgame,
            BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", crepository.findAll());
            model.addAttribute ("editBoardgame", boardgame);
            return "editboardgamewithvalidation";
        }
        repository.save(boardgame);
        return "redirect:/boardgamelist";
    }
    
}
