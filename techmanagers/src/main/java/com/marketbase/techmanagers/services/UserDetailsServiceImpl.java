//package com.marketbase.techmanagers.services;
//
//import com.marketbase.app.models.User;
//import com.marketbase.app.models.UserRole;
//import com.marketbase.app.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Optional;
//
//@Service("userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Transactional(readOnly = true)
//	public UserDetails loadUserByUsername(String username) {
//		Optional<User> user = userRepository.findByUsername(username);
//		if (user.isPresent()) {
//			User u = user.get();
//
//			String password = u.getPassword();
//			boolean enabled = u.isActive();
//			boolean accountNonExpired = u.isActive();
//			boolean credentialsNonExpired = u.isActive();
//			boolean accountNonLocked = u.isActive();
//
//			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//			for (UserRole r : u.getRoles()) {
//				authorities.add(new SimpleGrantedAuthority(r.toString()));
//			}
//			return new org.springframework.security.core.userdetails.User(
//					username, password, enabled, accountNonExpired,
//					credentialsNonExpired, accountNonLocked, authorities);
//		} else {
//			throw new UsernameNotFoundException(
//					"Unable to find user with username provided.");
//		}
//	}
//}