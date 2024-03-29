package com.example.cms.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class BlogRequestdto 
{
	@Pattern(regexp = "/^[a-zA-Z]+$/")
	private String title;
	@Column(nullable = false)
	private String topics[];
	@Column(nullable = false)
	private String about;

}
