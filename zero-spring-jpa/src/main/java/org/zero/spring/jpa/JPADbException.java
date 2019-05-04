package org.zero.spring.jpa;

public class JPADbException extends RuntimeException{

	private static final long serialVersionUID = -782067128349256405L;

	public JPADbException() {
		
	}
	
	public JPADbException(String msg) {
		super(msg);
	}
}
