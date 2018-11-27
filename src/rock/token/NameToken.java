package rock.token;

public class NameToken extends Token {

    public NameToken(int lineNumber, String name) {
        super(lineNumber, TokenType.NAME, name);
    }


    @Override
    public Object value() {
        return literal();
    }
}
