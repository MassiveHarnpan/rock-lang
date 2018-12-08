package rock.exception;

import java.util.Arrays;

public class UnsupportedOperationException extends RockException {

    private String operation;
    private String[] object;

    public UnsupportedOperationException(String operation, String... objects) {
        super("cannot apply " + operation + " to " + Arrays.asList(objects));
        this.operation = operation;
        this.object = object;
    }
}
