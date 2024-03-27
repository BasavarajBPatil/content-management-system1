package com.example.cms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CustomUserDetailService  implements UserDetailsService{
  @Autowired
	private UserRepository userRepository;
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
	return	userRepository.findByEmail(username)
			.map(user->new CustomUserDetails(user))
		   .orElseThrow(()->new UsernameNotFoundException("user not found with given username "+username));
		
	
	}

}
