package rock.token;

public class IdToken extends Token {
    private String text;

    public IdToken(int lineNumber, String literal, String text) {
        super(lineNumber, TokenType.IDENTIFIER, literal);
        this.text = text;
    }

    public IdToken(int lineNumber, String text) {
        super(lineNumber, TokenType.IDENTIFIER, text);
        this.text = text;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String getStr() {
        return text;
    }

    @Override
    public Object value() {
        return text;
    }
}
