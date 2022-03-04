package bankApplication.controllerExceptionHandling;

/**
 * Exception class to throw when there is no entity with such id in the database.
 */
public class NoSuchIdException extends RuntimeException {

    public NoSuchIdException(String message){
        super(message);
    }
}
