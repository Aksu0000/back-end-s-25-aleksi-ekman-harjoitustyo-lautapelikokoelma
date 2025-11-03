package backend25.boardgames.domain;

import org.springframework.data.repository.CrudRepository;      // Tuodaan Spring Data JPA:n CrudRepository, joka tarjoaa valmiit CRUD-toiminnot (Create, Read, Update, Delete).

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long>{ // Määritellään rajapinta käyttäjien tietokantaoperaatioihin. Extends CrudRepository<ApplicationUser, Long> tarkoittaa, että: tämä rajapinta käsittelee ApplicationUser-entiteettiä ja entiteetin ID on tyyppiä Long. CrudRepository tuo valmiit metodit kuten save(), findById(), findAll(), delete() jne.
    
    ApplicationUser findByUsername(String username);    // Custom-metodi, jonka Spring Data JPA toteuttaa automaattisesti. Hakee ApplicationUser-olion tietokannasta käyttäjätunnuksen perusteella.

}
