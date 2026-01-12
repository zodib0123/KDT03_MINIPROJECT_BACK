package com.ruby.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 7770953867607345417L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
