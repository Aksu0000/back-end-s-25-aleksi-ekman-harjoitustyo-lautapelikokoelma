package backend25.boardgames.domain;

import java.util.List; // Tuodaan List-kokoelma, jota käytetään palautustyypissä.

import org.springframework.data.repository.CrudRepository;  // Tuodaan Spring Data JPA:n CrudRepository, joka tarjoaa valmiit CRUD-metodit.

public interface BoardgameRepository extends CrudRepository<Boardgame, Long> {  // Rajapinta, joka käsittelee Boardgame-entiteettiä.
    List<Boardgame> findByTitleContainingIgnoreCase(String title);      // Custom-metodi, jonka Spring Data JPA toteuttaa automaattisesti nimen perusteella. Hakee kaikki pelit, joiden title sisältää annetun merkkijonon riippumatta kirjainkoosta. Palautustyyppi on List<Boardgame>, koska voi löytyä useampi tulos.

}
