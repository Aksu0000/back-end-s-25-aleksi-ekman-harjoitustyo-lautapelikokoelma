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

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // CSRF pois vain REST API:ltä
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/koti", "/login").permitAll()
                        .requestMatchers("/deleteGame/**").hasAuthority("ADMIN") // MVC DELETE
                        .requestMatchers(HttpMethod.DELETE, "/api/boardgames/**").hasAuthority("ADMIN") // REST DELETE
                        .requestMatchers("/api/**").permitAll()  // muut API:t sallitaan kaikille
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")                                        // oma login-sivu
                        .defaultSuccessUrl("/koti", true)                       // loginin jälkeen home
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/koti")                          // logout jälkeen booklist
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                // jos ei autentikoitu, ohjaa home-sivulle loginin sijaan
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/koti");
                        })
                );
                
                //.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
