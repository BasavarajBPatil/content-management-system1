package com.example.cms.serviceimpl;

import java.lang.foreign.Linker.Option;
import java.security.cert.CertificateEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.UserRequestdto;
import com.example.cms.dto.UserResponsedto;
import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistsByEmailException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResponseStructure<User> structure;
	@Autowired
	private ResponseStructure<UserResponsedto> structureResponse;

	PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<ResponseStructure<UserResponsedto>> saveUser(UserRequestdto user) {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new UserAlreadyExistsByEmailException("");
		User u = userRepository.save(mapToUserEntity(user));

		return ResponseEntity.ok(structureResponse.setStatusCode(HttpStatus.OK.value()).setData(mapToUserResponse(u))
				.setMessage("user created"));
	}

	private User mapToUserEntity(UserRequestdto user) {
		User u = new User();
		u.setEmail(user.getEmail());
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		u.setUserName(user.getUserName());
		u.setDeleted(false);
		return u;
	}

	private UserResponsedto mapToUserResponse(User user) {
		return UserResponsedto.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
				.createdAt(user.getCreatedAt()).modifiedAt(user.getLastModify()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponsedto>> getUser(int userId) {
		return userRepository.findById(userId)
				.map(user -> ResponseEntity.status(HttpStatus.FOUND)
						.body(structureResponse.setData(mapToUserResponse(user)).setMessage("user found")
								.setStatusCode(HttpStatus.FOUND.value())))
				.orElseThrow(() -> new UserNotFoundException("user not found with given id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponsedto>> deleteUser(int userId) {
		/*
		 * return userRepository.findById(userId).map(user ->
		 * ResponseEntity.status(HttpStatus.OK)
		 * .body(structureResponse.setData(mapToUserSoftdelete(user))
		 * .setMessage("user found") .setStatusCode(HttpStatus.OK.value())))
		 * .orElseThrow(()->new UserNotFoundException("user not found with given id"));
		 * 
		 */

		// or

		return userRepository.findById(userId).map(user -> {
			user.setDeleted(true);
			user = userRepository.save(user);
			return ResponseEntity.status(HttpStatus.OK.value()).body(
					structureResponse.setData(mapToUserResponse(user))
					.setMessage("Deleted user")
					.setStatusCode(HttpStatus.OK.value()));
		}).orElseThrow(() -> new UserNotFoundException("user not found with given id"));

	}
    //not required
	private UserResponsedto mapToUserSoftdelete(User user) 
	{
		user.setDeleted(true);
		userRepository.save(user);
		return mapToUserResponse(user);
	}
}
