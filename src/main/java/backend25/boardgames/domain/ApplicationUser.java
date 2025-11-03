package backend25.boardgames.domain;    // Määrittelee paketin, jossa luokka sijaitsee. Tämä auttaa Spring Bootia ja Javaa löytämään luokan.

import jakarta.persistence.*;   // Tuodaan JPA-annotaatiot, joita käytetään tietokantataulujen ja -sarakkeiden määrittelyyn.

@Entity       // Merkitsee luokan JPA-entiteetiksi eli tietokantataulukon malliksi.
@Table(name="application_user")     // Määrittää, että tämä entiteetti tallennetaan tietokantaan tauluna "application_user".
public class ApplicationUser {  // Luokan määritelmä käyttäjää varten.

    @Id // Määrittää kentän primääriavaimeksi.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Automaattisesti generoidaan ID tietokannan hallinnan avulla (esim. AUTO_INCREMENT).
	@Column(name = "id", nullable = false, updatable = false)   // Sarakkeen nimi tietokannassa on "id", ei voi olla null ja sitä ei voi muuttaa luomisen jälkeen.
	private Long id;        // Tallentaa käyttäjän yksilöllisen tunnisteen (ID).

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;
	
	@Column(name = "username", nullable = false, unique = true)     // käyttäjänimi yksilöllisellä (uniikilla) constraintilla
	private String username;
	
	@Column(name = "application_password", nullable = false)
	private String passwordHash;        // Tallentaa käyttäjän salasanan hashatun version tietoturvan vuoksi.
	
	@Column(name = "application_role", nullable = false)
	private String role;

    public ApplicationUser() {      // Tyhjä konstruktori, jota JPA tarvitsee entiteetin luomiseen.
    }

    public ApplicationUser(String firstName, String lastName, String username, String passwordHash, String role) {   // Konstruktori, jolla voidaan luoda käyttäjäobjekti valmiilla tiedoilla.
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override       // Ylikirjoittaa toString-metodin, jotta objektin tiedot voidaan tulostaa helposti esimerkiksi debuggausta varten.
    public String toString() {
        return "AppUser [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", role=" + role
                + "]";
    }
    
}
