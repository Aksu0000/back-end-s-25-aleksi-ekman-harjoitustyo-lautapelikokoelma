package backend25.boardgames.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore; // Tuodaan Jackson-anotointi, jolla voidaan estää tietojen sarjoittaminen JSONiksi.

import jakarta.persistence.*; // Tuodaan JPA-anotoinnit tietokantataulujen ja -suhteiden määrittelyyn.

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre_name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genre")   // Lista Boardgame-olioista, jotka kuuluvat tähän genreen. @OneToMany kertoo, että yhdellä genrellä voi olla monta peliä. cascade = ALL tarkoittaa, että genren muutokset vaikuttavat myös peleihin (esim. poisto). mappedBy = "genre" määrittää, että Boardgame-olion genre-ominaisuus hallitsee suhdetta. @JsonIgnore estää sen, että JSON-vastauksessa tämä lista aiheuttaisi loputtoman silmukan.
    @JsonIgnore
    private List<Boardgame> boardgames;

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Boardgame> getBoardgames() {
        return boardgames;
    }

    public void setBoardgames(List<Boardgame> boardgames) {
        this.boardgames = boardgames;
    }
}
