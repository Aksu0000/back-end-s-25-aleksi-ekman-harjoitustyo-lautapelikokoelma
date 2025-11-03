package backend25.boardgames.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;      // Tuodaan validointianotoinnit (NotEmpty, NotNull, Min, Size) käyttäjältä tulevien arvojen tarkistamiseen.

@Entity     // Merkitsee luokan JPA-entiteetiksi eli tietokantataulukon malliksi.
@Table(name = "game")
public class Boardgame {    // Luokka kuvaa yksittäistä lautapeliä.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @NotEmpty(message = "{NotEmpty.boardgame.title}")
    @Size(min = 1, max = 250, message = "{Size.boardgame.title}")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "{NotNull.boardgame.publicationYear}")
    @Min(value = 0, message = "{Min.boardgame.publicationYear}")
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @NotNull(message = "{NotNull.boardgame.minPlayers}")
    @Min(value = 1, message = "{Min.boardgame.minPlayers}")
    @Column(name = "min_players", nullable = false)
    private Integer minPlayers;

    @NotNull(message = "{NotNull.boardgame.maxPlayers}")
    @Min(value = 1, message = "{Min.boardgame.maxPlayers}")
    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @NotNull(message = "{NotNull.boardgame.genre}")
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Boardgame() {        // Tyhjä konstruktori JPA:lle.
    }

    public Boardgame(String title, Integer publicationYear, Integer minPlayers, Integer maxPlayers, Genre genre, String imageUrl) { // Konstruktori, jolla voidaan luoda Boardgame-olio valmiilla tiedoilla.
        this.title = title;
        this.publicationYear = publicationYear;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.genre = genre;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override       // Ylikirjoitetaan toString-metodi, jotta objektin tiedot voidaan tulostaa helposti. Näyttää pelin nimen, julkaisuvuoden ja genren nimen (tai "Ei genreä" jos null).
    public String toString() {
        return title + publicationYear + " - " +
                (genre != null ? genre.getName() : "Ei genreä");
    }

}
