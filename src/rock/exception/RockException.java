package rock.exception;

import rock.token.Token;

import java.io.IOException;

public class RockException extends Exception {


    private static String location(Token token) {
        if (token == Token.EOF) {
            return "EOF";
        } else {
            return token.literal() + " at " + token.getPos();
        }
    }

    private Token token;

    public Token getToken() {
        return token;
    }

    public RockException(Token token) {
        super("bad token at " + location(token));
        this.token = token;
    }

    public RockException(String message, Token token) {
        super(message + " at " + location(token));
        this.token = token;
    }

    public RockException(String m) {
        super(m);
    }


    public RockException(Exception e) {
        super(e);
    }
}
