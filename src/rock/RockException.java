package rock;

import java.io.IOException;

public class RockException extends Exception {


    private static String location(Token token) {
        if (token == Token.EOF) {
            return "EOF";
        } else {
            return "\"" + token.getText() + "\" at line:" + token.getLineNumber();
        }
    }


    public RockException(String m) {
        super(m);
    }


    public RockException(IOException e) {
        super(e);
    }
}
