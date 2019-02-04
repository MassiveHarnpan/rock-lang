package rock.token;

public class StrToken extends Token {

    private String str;

    public StrToken(int lineNumber, int offsetNumber, String literal, String str) {
        super(lineNumber, offsetNumber, TokenType.STRING, literal);
        this.str = str;
    }

    @Override
    public String getStr() {
        return str;
    }

    @Override
    public Object value() {
        return str;
    }
}
