package br.com.lutamber.simplesdental.exception;

public class ResourceNotFoundException extends NoStackTraceException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
