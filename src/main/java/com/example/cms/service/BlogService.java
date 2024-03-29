package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.BlogRequestdto;
import com.example.cms.dto.BlogResponseDto;
import com.example.cms.utility.ResponseStructure;

public interface BlogService 
{

	ResponseEntity<ResponseStructure<BlogResponseDto>> registerBlog(int userId,BlogRequestdto blogRequestdto);

	ResponseEntity<ResponseStructure<Boolean>> checkAvailability(String title);

	ResponseEntity<ResponseStructure<BlogResponseDto>> getBlog(int blogId);

	ResponseEntity<ResponseStructure<BlogResponseDto>> updateBlog(int blogId,BlogRequestdto blogRequestdto);

}
