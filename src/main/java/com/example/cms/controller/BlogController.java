package com.example.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.BlogRequestdto;
import com.example.cms.dto.BlogResponseDto;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class BlogController {

	@Autowired
	private BlogService blogService;

	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponseDto>> createBlog(@PathVariable int userId,
			@Valid @RequestBody BlogRequestdto blogRequestdto) {
		return blogService.registerBlog(userId, blogRequestdto);
	}

	@GetMapping("/titles/{title}/blogs")
	public ResponseEntity<ResponseStructure<Boolean>> isAvailable(@PathVariable String title) {
		return blogService.checkAvailability(title);
	}

	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponseDto>> fetchById(@PathVariable int blogId) {
		return blogService.getBlog(blogId);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponseDto>> updateBlog(@PathVariable int blogId,@RequestBody @Valid BlogRequestdto blogRequestdto)
	{
		return blogService.updateBlog(blogId,blogRequestdto);
	}
}
