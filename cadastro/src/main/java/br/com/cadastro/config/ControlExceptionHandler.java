package br.com.cadastro.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cadastro.exception.UsuarioNaoEncontradoException;

@ControllerAdvice
public class ControlExceptionHandler {

	@ExceptionHandler(value = { UsuarioNaoEncontradoException.class })
	protected ResponseEntity<Object> handleConflict(UsuarioNaoEncontradoException uException, WebRequest request) {
		return ResponseEntity.status(404).body(uException.getMessage());
	}

}
