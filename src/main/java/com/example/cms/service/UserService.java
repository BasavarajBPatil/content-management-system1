package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.UserRequestdto;
import com.example.cms.dto.UserResponsedto;
import com.example.cms.entity.User;
import com.example.cms.utility.ResponseStructure;

public interface UserService
{
	

	 ResponseEntity<ResponseStructure<UserResponsedto>> saveUser(UserRequestdto user) ;

	ResponseEntity<ResponseStructure<UserResponsedto>> getUser(int userId);

	ResponseEntity<ResponseStructure<UserResponsedto>> deleteUser(int userId);

}
