package com.rockgustavo.api.exception;

import java.io.Serializable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<?> erroNotFound(Exception ex) {
		System.out.println("erroNotFound");
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<?> errorBadRequest(Exception ex) {
		System.out.println("errorBadRequest");
		return ResponseEntity.badRequest().build();
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new ExceptionError("Operação Não Permitida"), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler({ AccessDeniedException.class, NotFoundException.class })
	public ResponseEntity<?> accessDenied() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso negado"));
	}
}

@SuppressWarnings("serial")
class ExceptionError implements Serializable {
	private String error;

	ExceptionError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
