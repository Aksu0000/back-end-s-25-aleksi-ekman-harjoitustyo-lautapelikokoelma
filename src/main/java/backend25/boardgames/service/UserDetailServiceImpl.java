package backend25.boardgames.service;

import org.springframework.security.core.authority.AuthorityUtils;		// Tuodaan apuluokka, jolla voidaan luoda lista käyttäjän rooleista.
import org.springframework.security.core.userdetails.UserDetails;		// Spring Securityn rajapinta, joka kuvaa kirjautuneen käyttäjän tietoja.
import org.springframework.security.core.userdetails.UserDetailsService;	// Rajapinta, jonka avulla voidaan ladata käyttäjän tiedot autentikointia varten.
import org.springframework.security.core.userdetails.UsernameNotFoundException;	// Poikkeus, joka heitetään jos käyttäjää ei löydy tietokannasta.
import org.springframework.stereotype.Service;			// Merkitsee luokan palvelukomponentiksi Springille.

import backend25.boardgames.domain.ApplicationUser;		// Tuodaan ApplicationUser-entiteetti.
import backend25.boardgames.domain.ApplicationUserRepository;	// Tuodaan repository käyttäjien hakemiseen.

@Service														// Merkitsee, että Spring hallinnoi tätä luokkaa palveluna ja tekee siitä beanin.
public class UserDetailServiceImpl implements UserDetailsService {	 // Luokka toteuttaa UserDetailsService-rajapinnan, jotta Spring Security voi käyttää sitä autentikointiin.
    private final ApplicationUserRepository repository;			// Repository, jota käytetään käyttäjien hakemiseen tietokannasta.

    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {	// Konstruktori, jolla repository injektoidaan luokkaan.
		this.repository = applicationUserRepository;
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	// Metodi, jota Spring Security kutsuu kirjautumisen yhteydessä.
		ApplicationUser currentuser = repository.findByUsername(username);		// Haetaan käyttäjä tietokannasta käyttäjätunnuksen perusteella.
		if (currentuser == null) {
			throw new UsernameNotFoundException("Käyttäjää ei löydy");	// Jos käyttäjää ei löydy, heitetään poikkeus.
		}
		UserDetails user = new org.springframework.security.core.userdetails.User(username, currentuser.getPasswordHash(),	// Luodaan UserDetails-olio Spring Securitya varten. username: kirjautumistunnus, password: hashattu salasana, roolit: luodaan AuthorityList käyttäjän roolista.
				AuthorityUtils.createAuthorityList(currentuser.getRole()));
		return user;		// Palautetaan UserDetails Spring Securityn käyttöön autentikointia varten.
	}
}
