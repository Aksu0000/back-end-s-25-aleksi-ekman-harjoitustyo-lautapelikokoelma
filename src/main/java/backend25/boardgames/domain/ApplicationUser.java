package backend25.boardgames.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "application_user")
public class ApplicationUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(name = "application_password")
    private String password;

    @Column(name = "application_role")
    private String role;

    @OneToMany(mappedBy = "addedBy")
    private List<Game> games;

    public ApplicationUser() {}

        // getterit ja setterit

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}

