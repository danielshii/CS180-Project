package exception;

import enumeration.ObjectType;

public class InvalidObjectTypeException extends RuntimeException {
    public InvalidObjectTypeException(ObjectType objectType) {
        super("Object type is invalid: " + objectType.name());
    }
}
