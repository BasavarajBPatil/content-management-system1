package com.example.cms.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.dto.BlogRequestdto;
import com.example.cms.dto.BlogResponseDto;
import com.example.cms.entity.Blog;
import com.example.cms.entity.User;
import com.example.cms.exception.BlogNotFoundWithId;
import com.example.cms.exception.BlogTitleNotAvailableException;
import com.example.cms.exception.TopicsNotFoundException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

	private BlogRepository blogRepository;
	private ResponseStructure<BlogResponseDto> structure;
	private UserRepository userRepository;
	private ResponseStructure<Boolean> booleanstructre;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseDto>> registerBlog(int userId, BlogRequestdto blogRequestdto) {
		return userRepository.findById(userId).map(user -> {

			if (blogRepository.existsByTitle(blogRequestdto.getTitle()))
				throw new BlogTitleNotAvailableException("Failed  to create blog");
			if (blogRequestdto.getTopics().length < 1)
				throw new TopicsNotFoundException("Failed to create blog");

			Blog blog = mapToBlogEntity(blogRequestdto, new Blog());
			blog.setUsers(Arrays.asList(user));

			blog = blogRepository.save(blog);
			return ResponseEntity.status(HttpStatus.CREATED).body(structure.setStatusCode(HttpStatus.CREATED.value())
					.setMessage("Blog created successfully").setData(mapToBlogResponse(blog)));
		}).orElseThrow(() -> new UserNotFoundException("Failed to create blog"));
	}

	private BlogResponseDto mapToBlogResponse(Blog blog) {
		return BlogResponseDto.builder().title(blog.getTitle()).about(blog.getAbout()).topics(blog.getTopics())
				.blogId(blog.getBlogId()).build();
	}

	private Blog mapToBlogEntity(BlogRequestdto blogRequestdto, Blog blog) {
		blog.setTitle(blogRequestdto.getTitle());
		blog.setAbout(blogRequestdto.getAbout());
		blog.setTopics(blogRequestdto.getTopics());

		return blog;
	}

	@Override
	public ResponseEntity<ResponseStructure<Boolean>> checkAvailability(String title) {
		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs) {
			if (blog.getTitle().equals(title))
				return ResponseEntity.status(HttpStatus.CREATED).body(booleanstructre
						.setStatusCode(HttpStatus.FOUND.value()).setMessage("title not available").setData(false));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(booleanstructre
				.setStatusCode(HttpStatus.NOT_FOUND.value()).setMessage("title available").setData(true));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseDto>> getBlog(int blogId) {
		return blogRepository.findById(blogId)
				.map(blog -> ResponseEntity.status(HttpStatus.FOUND)
						.body(structure.setData(mapToBlogResponse(blog)).setMessage("Blopg found")
								.setStatusCode(HttpStatus.FOUND.value())))
				.orElseThrow(() -> new BlogNotFoundWithId("Not Found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseDto>> updateBlog(int blogId,BlogRequestdto blogRequestdto) {
		return blogRepository.findById(blogId).map(blog -> {
/*
			BlogRequestdto blogRequestdto = BlogRequestdto.builder()
					.about("simplychanged to some other title")
					.topics(new String[] { "java", "python", "c", "javaScript" })
					.title("againsimple")
					.build();
*/
			blog = mapToBlogEntity(blogRequestdto, new Blog());
			if (blogRepository.existsByTitle(blogRequestdto.getTitle()))
				throw new BlogTitleNotAvailableException("Failed  to create blog");
			if (blogRequestdto.getTopics().length < 1)
				throw new TopicsNotFoundException("Failed to create blog");
			blog.setBlogId(blogId);
			blogRepository.save(blog);

			return ResponseEntity.status(HttpStatus.OK).body(structure.setData(mapToBlogResponse(blog))
					.setMessage("Blopg updated").setStatusCode(HttpStatus.FOUND.value()));
		}).orElseThrow(() -> new BlogNotFoundWithId("Fail to update blog"));
	}

}
