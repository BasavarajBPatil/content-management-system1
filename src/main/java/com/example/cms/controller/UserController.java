package com.example.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.UserRequestdto;
import com.example.cms.dto.UserResponsedto;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class UserController 
{
	@Autowired
	private UserService userService;
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponsedto>> registerUser(@RequestBody @Valid UserRequestdto   user)
	{
		
		return userService.saveUser(user);
	}
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponsedto>> fetchUser(@PathVariable int userId )
	{
		return userService.getUser(userId);
	}
	@GetMapping("/users/test")
	public String getMessage()
	{
		return "hello message from cms";
	}
	@DeleteMapping("/users/{userId}")
	private ResponseEntity<ResponseStructure<UserResponsedto>> softdelete(@PathVariable int userId)
	{
		return userService.deleteUser(userId);
	}
	
}
