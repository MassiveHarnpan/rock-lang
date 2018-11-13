package rock;

import rock.token.Token;

import java.io.IOException;

public class RockException extends Exception {


    public RockException(Exception e) {
        super(e);
    }

    public RockException(String message, Exception e) {
        super(message, e);
    }

    private static String location(Token token) {
        if (token == Token.EOF) {
            return "EOF";
        } else {
            return "\"" + token.getStr() + "\" at line:" + token.getLineNumber();
        }
    }


    public RockException(String m) {
        super(m);
    }


    public RockException(IOException e) {
        super(e);
    }
}
