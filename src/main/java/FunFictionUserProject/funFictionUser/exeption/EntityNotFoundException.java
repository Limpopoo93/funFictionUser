package FunFictionUserProject.funFictionUser.exeption;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
