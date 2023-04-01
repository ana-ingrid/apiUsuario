package br.com.cadastro.exception;

public class UsuarioExistenteException extends Exception {


	private static final long serialVersionUID = 1L;

	public UsuarioExistenteException(String string) {
		super(string);
	}
	
}
