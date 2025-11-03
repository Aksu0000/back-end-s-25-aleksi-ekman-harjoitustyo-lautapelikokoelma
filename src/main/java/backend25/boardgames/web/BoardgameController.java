package backend25.boardgames.web;

import org.springframework.security.access.prepost.PreAuthorize;    // Tuodaan anotaatio, jolla voidaan rajoittaa pääsyä metodeihin roolien perusteella.
import org.springframework.stereotype.Controller;       // Merkitsee luokan Spring MVC -kontrolleriksi.
import org.springframework.web.bind.annotation.*;       // Tuodaan anotaatioita HTTP-pyyntöjen käsittelyyn (@GetMapping, @PostMapping, @PathVariable, @ModelAttribute).
import org.springframework.web.bind.WebDataBinder;      // Käytetään lomakedatan sitomiseen objekteihin ja mukautettujen editorien rekisteröintiin.
import org.springframework.ui.Model;                    // Malli, johon lisätään attribuutteja näkymää varten.
import org.springframework.validation.BindingResult;    // Tallentaa lomakevalidoinnin tulokset.

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import backend25.boardgames.domain.GenreRepository;
import jakarta.validation.Valid;                        // Anotaatio, jolla Spring validoi lomakkeelta tulevat arvot.
import java.beans.PropertyEditorSupport;                // Käytetään mukautettujen editorien tekemiseen, esim. String → Integer muunnos.


@Controller     // Merkitsee luokan Spring MVC -kontrolleriksi, joka käsittelee web-pyyntöjä.

public class BoardgameController {

    private final BoardgameRepository repository;       // Repositoriot tietokannan käsittelyyn.
    private final GenreRepository crepository;

	public BoardgameController(BoardgameRepository repository, GenreRepository crepository) {    // Konstruktori, jolla repositoriot injektoidaan.
		this.repository = repository;
        this.crepository = crepository;
	}

    @InitBinder
    public void initBinder(WebDataBinder binder) {      // Metodi, jolla määritellään mukautettu editori lomakedatan sitomiseen.
        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    if (text == null || text.trim().isEmpty()) {    // Jos kenttä tyhjä, asetetaan null, jolloin @NotNull validoi.
                        setValue(null);
                    } else {
                        setValue(Integer.parseInt(text));   // Muunnetaan merkkijono kokonaisluvuksi.
                    }
                } catch (NumberFormatException e) { // Jos ei voida muuntaa, asetetaan null, jolloin @NotNull aktivoi virheen.
                    setValue(null); // Tyhjennä kenttä
                }
            }
        });
    }

    @GetMapping(value= {"/boardgamelist"})      // Käsittelee GET-pyynnön polkuun /boardgamelist
	public String boardgameList(Model model) {
		model.addAttribute("boardgames", repository.findAll()); // Lisää malliin kaikki Boardgame-oliot
		return "boardgamelist";     // Palauttaa näkymän boardgamelist.html
	}

    @GetMapping(value = "/add")
    public String addBoardgame(Model model) {
        model.addAttribute("boardgame", new Boardgame());
        model.addAttribute("genres", crepository.findAll());
        return "addboardgame";
    }

    @PostMapping(value = "/saveBoardgame")
    public String saveBoardgame(@Valid @ModelAttribute("boardgame") Boardgame boardgame,     // Käsittelee lomakkeen POST-pyynnön tallennukseen
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {                                // Jos validoinnissa virheitä, palautetaan sama lomake uudelleen
            model.addAttribute("genres", crepository.findAll());
            return "addboardgame";
        }
        repository.save(boardgame);         // Tallennetaan uusi Boardgame tietokantaan
        return "redirect:/boardgamelist";      // Ohjataan takaisin pelilistan näkymään
    }

    @PreAuthorize("hasAuthority('ADMIN')")      // Vain ADMIN-roolilla voi poistaa
    @GetMapping(value = "/deleteBoardgame/{id}")
    public String deleteBoardgame(@PathVariable("id") Long boardgameId, Model model) {
        repository.deleteById(boardgameId);     // Poistetaan peli tietokannasta
        return "redirect:../boardgamelist";
    }

    @GetMapping(value = "/editBoardgame/{id}")
    public String editBoardgame(@PathVariable("id") Long boardgameId, Model model) {
        Boardgame boardgame = repository.findById(boardgameId).get();   // Haetaan peli ID:n perusteella
        model.addAttribute("editBoardgame", boardgame);     // lisätään malliobjekti "boardgame", jota lomake käyttää
        model.addAttribute("genres", crepository.findAll());    // Lisätään genre-lista, jotta käyttäjä voi muuttaa genreä
        return "editboardgamewithvalidation";   // Palautetaan näkymä editboardgamewithvalidation.html
    }

    @PostMapping("/saveEditedBoardgame")
    public String saveEditedBoardgame(@Valid @ModelAttribute("editBoardgame") Boardgame boardgame,
            BindingResult bindingResult, Model model) {     // Käsittelee muokatun pelin tallennuksen
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", crepository.findAll());
            model.addAttribute ("editBoardgame", boardgame);
            return "editboardgamewithvalidation";       // Jos validoinnissa virheitä, palautetaan sama lomake uudelleen
        }
        repository.save(boardgame);     // Tallennetaan muokattu peli tietokantaan
        return "redirect:/boardgamelist";
    }
    
}
