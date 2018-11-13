package rock.token;

public class IdToken extends Token {
    private String name;

    public IdToken(int lineNumber, String literal, String name) {
        super(lineNumber, TokenType.NAME, literal);
        this.name = name;
    }

    public IdToken(int lineNumber, String name) {
        super(lineNumber, name);
        this.name = name;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String getStr() {
        return name;
    }

    @Override
    public Object value() {
        return name;
    }
}
