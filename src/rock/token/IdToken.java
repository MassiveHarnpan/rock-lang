package rock.token;

public class IdToken extends Token {
    private String text;

    public IdToken(int lineNumber, int offsetNumber, String text) {
        super(lineNumber, offsetNumber, TokenType.IDENTIFIER, text);
        this.text = text;
    }

    @Override
    public String getStr() {
        return text;
    }

    @Override
    public Object value() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
