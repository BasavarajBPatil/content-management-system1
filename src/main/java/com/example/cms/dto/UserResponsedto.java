package com.example.cms.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponsedto
{
	private int  userId;
	private String userName;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

}
