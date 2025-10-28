package backend25.boardgames.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend25.boardgames.domain.ApplicationUser;
import backend25.boardgames.domain.ApplicationUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final ApplicationUserRepository repository;

    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.repository = applicationUserRepository;
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser currentuser = repository.findByUsername(username);
		if (currentuser == null) {
			throw new UsernameNotFoundException("Käyttäjää ei löydy");
		}
		UserDetails user = new org.springframework.security.core.userdetails.User(username, currentuser.getPasswordHash(),
				AuthorityUtils.createAuthorityList(currentuser.getRole()));
		return user;
	}
}
