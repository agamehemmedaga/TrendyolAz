package az.trendyolaz.exception;


public class BadCredentialsExceptionCustom extends RuntimeException {
    public BadCredentialsExceptionCustom(String message) {
        super(message);
    }
}