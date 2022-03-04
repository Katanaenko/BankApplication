package bankApplication.controllerExceptionHandling;

/**
 * DTO class for exceptions.
 */
public class ExceptionDTO {

    public ExceptionDTO() {
    }

    public ExceptionDTO(String info) {
        this.info = info;
    }

    private String info;

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
