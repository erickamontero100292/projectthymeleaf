package com.workday.security;

import com.workday.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.workday.entity.EntityUserApp;
import com.workday.repository.UserAppRepository;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserAppRepository repository;
	@Autowired
	RolRepository repositoryRol;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EntityUserApp usuario = repository.findFirstByUser(username);
		UserBuilder builder = null;
		
		if (usuario != null) {
			BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuario.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getName()));
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		return builder.build();
	}

}
