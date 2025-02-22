package br.com.primary.main.exceptions;

public class TypeKeyNotFoundException extends Exception {
	private static final long serialVersionUID = -1858449494222984498L;
	public TypeKeyNotFoundException(String msg) {
		this(msg, null);
	}
	public TypeKeyNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
