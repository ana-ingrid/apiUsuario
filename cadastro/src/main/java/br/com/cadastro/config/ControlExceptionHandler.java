package br.com.cadastro.config;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cadastro.exception.UsuarioExistenteException;
import br.com.cadastro.exception.UsuarioNaoEncontradoException;

@ControllerAdvice
public class ControlExceptionHandler {

	@ExceptionHandler(value = { UsuarioNaoEncontradoException.class })
	protected ResponseEntity<Object> handleConflict(UsuarioNaoEncontradoException uException, WebRequest request) {
		return ResponseEntity.status(404).body(uException.getMessage());
	}

	@ExceptionHandler(value = {UsuarioExistenteException.class})
	protected ResponseEntity<Object> handle(UsuarioExistenteException uExistenteException, WebRequest request) {
		return ResponseEntity.status(409).body(uExistenteException.getMessage());
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<Object> handleConflictMa(MethodArgumentNotValidException mException, WebRequest request){
		List<ObjectError> erros = mException.getAllErrors();
		return ResponseEntity.status(400).body(erros.get(0).getDefaultMessage());
	}
}
