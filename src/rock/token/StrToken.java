package rock.token;

public class StrToken extends Token {

    private String str;

    public StrToken(int lineNumber, String literal, String str) {
        super(lineNumber, TokenType.STRING, literal);
        this.str = str;
    }

    @Override
    public boolean isString() {
        return true;
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
