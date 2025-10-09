package backend25.boardgames.web;

import backend25.boardgames.dto.BoardGameDto;
import backend25.boardgames.service.GameService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Controller
@RequestMapping("/external")
public class ExternalGameController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GameService gameService;

    public ExternalGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/search")
    public String searchGames(@RequestParam String query, Model model) {
        String url = "https://bgg-json.azurewebsites.net/search?query=" + query;
        ResponseEntity<List<BoardGameDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BoardGameDto>>() {
                });
        model.addAttribute("results", response.getBody());
        return "search";
    }

    @PostMapping("/addFromApi")
    public String addFromApi(@ModelAttribute BoardGameDto dto) {
        gameService.addGameFromApi(dto);
        return "redirect:/games";
    }
}