package it.jac.corsojava.exception;

public class DaoException extends RuntimeException {

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable th) {
		super(message, th);
	}
}
