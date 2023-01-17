package africa.tmannazy.employeemgt.exceptions;

public class ResourceNotFoundException extends Exception{
    private static final Long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
