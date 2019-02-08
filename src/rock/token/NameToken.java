package rock.token;

public class NameToken extends Token {

    public NameToken(int lineNumber, int offsetNumber, String name) {
        super(lineNumber, offsetNumber, TokenType.NAME, name);
    }


    @Override
    public Object value() {
        return literal();
    }

    @Override
    public String toString() {
        return literal();
    }
}
