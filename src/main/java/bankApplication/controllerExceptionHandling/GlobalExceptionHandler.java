package bankApplication.controllerExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.OptimisticLockException;

/**
 * Exception handler class with methods to handle exceptions raised during execution of the program
 * and sending info about the exception to a client.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Catch NoSuchIdException(there is no row with such id in a table)
     * exception and send info about it to the client.
     * @param exception the exception raised during execution of the program
     * @return response with the exception info and HttpStatus to the client
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> noSuchIdExceptionHandler(NoSuchIdException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Catch OptimisticLockException(several transactions are trying to update a row at the same moment)
     * exception and send info about it to the client.
     * @param exception the exception raised during execution of the program
     * @return response with exception info and HttpStatus to the client
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> OptimisticLockExceptionHandler(OptimisticLockException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Try your operation again");
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    /**
     * Catch exceptions which are not handled individually.
     * @param exception the exception raised during execution of the program
     * @return response with exception info and HttpStatus to the client
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> otherExceptionHandler(Throwable exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
