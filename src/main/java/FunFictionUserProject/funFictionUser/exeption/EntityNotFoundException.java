package FunFictionUserProject.funFictionUser.exeption;

public class EntityNotFoundException extends RuntimeException {

    private static final String MESSAGE_ID_TEMPLATE = "%s with id=%s not found";

    private static final String MESSAGE_TEMPLATE = "%s not found";

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String entityName) {
        super(String.format(MESSAGE_TEMPLATE, entityName));
    }

    public EntityNotFoundException(String entity, String id) {
        super(String.format(MESSAGE_ID_TEMPLATE, entity, id));
    }

    public EntityNotFoundException(String entity, Long id) {
        super(String.format(MESSAGE_ID_TEMPLATE, entity, id));
    }

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(String.format(MESSAGE_ID_TEMPLATE, entityClass.getSimpleName(), id));
    }
}
