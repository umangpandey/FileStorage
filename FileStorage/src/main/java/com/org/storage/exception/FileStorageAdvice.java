package com.org.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileStorageAdvice {

	@ExceptionHandler({FileNotFoundException.class,FileAlreadyExistException.class,InvalidFileExtension.class})
	public ResponseEntity<String> handleCustomException(Exception e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> handleOtherException(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
}
