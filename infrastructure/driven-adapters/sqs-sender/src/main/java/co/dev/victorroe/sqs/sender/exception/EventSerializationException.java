package co.dev.victorroe.sqs.sender.exception;

public class EventSerializationException extends RuntimeException {

    public EventSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
