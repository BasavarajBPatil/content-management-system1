package com.example.cms.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.util.Reflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationHandler extends ResponseEntityExceptionHandler {
	@Autowired
private ErrorStructure errorstructure;
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	
		List<ObjectError> listError = ex.getAllErrors();

		Map<String, String> messages=new HashMap<>();
		
		listError.forEach(error -> {
			FieldError fieldError = (FieldError) error;
			messages.put(fieldError.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errorstructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setRootCause(messages)
				.setMessage("invalid Input"));
	}

}
