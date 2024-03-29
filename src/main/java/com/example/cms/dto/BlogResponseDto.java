package com.example.cms.dto;

import java.util.List;

import com.example.cms.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BlogResponseDto 
{

	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	private List<User> users;
	
	
}
