package br.com.primary.main.service.exceptions;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
