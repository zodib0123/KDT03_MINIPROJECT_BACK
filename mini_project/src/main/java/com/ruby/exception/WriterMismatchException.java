package com.ruby.exception;

public class WriterMismatchException extends RuntimeException{

	private static final long serialVersionUID = 2261155036321029779L;
	
	public WriterMismatchException(String msg) {
		super(msg);
	}
}
