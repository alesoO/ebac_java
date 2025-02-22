package br.com.primary.main.exceptions;

public class DaoException extends Exception {
	private static final long serialVersionUID = 7054379063290825137L;
	public DaoException(String msg, Exception ex) {
		super(msg, ex);
	}
}
