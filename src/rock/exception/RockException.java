package rock.exception;

import rock.token.Token;

import java.io.IOException;

public class RockException extends Exception {


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


    public RockException(Exception e) {
        super(e);
    }
}
