package br.com.cadastro.controller;

import br.com.cadastro.exceptions.ErroConversaoException;
import br.com.cadastro.exceptions.RecursoExistenteException;
import br.com.cadastro.exceptions.RecursoNaoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class ControlExceptionHandler {

	@ExceptionHandler(value = { RecursoNaoEncontradoException.class })
	protected ResponseEntity<Object> usuarioNaoEncontrado (RecursoNaoEncontradoException uException, WebRequest request) {
		return ResponseEntity.status(404).body(uException.getMessage());
	}

	@ExceptionHandler(value = {RecursoExistenteException.class})
	protected ResponseEntity<Object> usuarioExistente (RecursoExistenteException uExistenteException, WebRequest request) {
		return ResponseEntity.status(409).body(uExistenteException.getMessage());
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<Object> methodArgumentNotValid (MethodArgumentNotValidException mException, WebRequest request){
		List<ObjectError> erros = mException.getAllErrors();
		return ResponseEntity.status(400).body(erros.get(0).getDefaultMessage());
	}

	@ExceptionHandler(value = {ErroConversaoException.class})
	protected ResponseEntity<Object> ErroConversao (ErroConversaoException uException, WebRequest request) {
		return ResponseEntity.status(500).body(uException.getMessage());
	}

}
