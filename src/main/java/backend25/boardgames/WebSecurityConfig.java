package backend25.boardgames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      // Merkitsee luokan konfiguraatioluokaksi Springille.
@EnableMethodSecurity(securedEnabled = true)        // Mahdollistaa @PreAuthorize ja @Secured -anotaatiot metoditasolla.
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;         // Palvelu, joka lataa käyttäjät tietokannasta autentikointia varten.

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;       // Konstruktori, jolla UserDetailsService injektoidaan.
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {       // Määrittelee autentikoinnin konfiguraation
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());   // Käytetään BCrypt-salausta salasanoille
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // Poistaa CSRF-suojauksen REST API -pyynnöiltä
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/koti", "/login").permitAll()  // Sallii kaikille pääsyn tyylitiedostoihin, kotisivuun ja login-sivulle
                        .requestMatchers("/deleteGame/**").hasAuthority("ADMIN") // MVC DELETE vaatii ADMIN-roolin
                        .requestMatchers(HttpMethod.DELETE, "/api/boardgames/**").hasAuthority("ADMIN") // REST DELETE vaatii ADMIN-roolin
                        .requestMatchers("/api/**").permitAll()  // muut API:t sallitaan kaikille
                        .anyRequest().authenticated()) // Kaikki muu vaatii kirjautumisen

                .formLogin(form -> form
                        .loginPage("/login")                                        // oma login-sivu
                        .defaultSuccessUrl("/koti", true)                      // Kirjautumisen jälkeen ohjataan kotisivulle
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/koti")                          // Logoutin jälkeen ohjataan kotisivulle
                        .invalidateHttpSession(true)                        // Tuhoa sessio
                        .permitAll()
                )

                // jos ei autentikoitu, ohjaa home-sivulle loginin sijaan
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/koti");
                        })
                );

        return http.build();    // Rakennetaan ja palautetaan SecurityFilterChain
    }
}
