package rock.exception;

import java.util.Arrays;

public class UnsupportedOperationException extends RockException {

    private String operation;
    private String[] object;

    public UnsupportedOperationException(String operation, String... objects) {
        super("cannot apply " + operation + " to " + (objects.length == 1 ? objects[0] : Arrays.asList(objects)));
        this.operation = operation;
        this.object = object;
    }
}
