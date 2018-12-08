package rock.exception;

import java.util.Arrays;

public class UnsupportedASTException extends RockException {

    private String get;
    private String[] expect;

    public UnsupportedASTException(String get, String... expect) {
        super("unsupported AST: expect: " + Arrays.asList(expect) + ", get: " + get);
        this.get = get;
        this.expect = expect;
    }
}
