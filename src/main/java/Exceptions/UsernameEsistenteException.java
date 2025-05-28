package Exceptions;

public class UsernameEsistenteException extends RuntimeException {
    public UsernameEsistenteException(String message) {
        super(message);
    }
}
