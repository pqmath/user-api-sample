package com.br.matthew.exceptions;

public class JdbcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JdbcException(String msg) {
		super(msg);
	}

}
