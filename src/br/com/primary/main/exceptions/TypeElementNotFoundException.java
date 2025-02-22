package br.com.primary.main.exceptions;

public class TypeElementNotFoundException extends Exception {
	private static final long serialVersionUID = -2268140970978666251L;
	public TypeElementNotFoundException(String msg) {
		this(msg, null);
	}
	public TypeElementNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
