package rock.token;

public class NumToken extends Token {

    private Number number;

    public NumToken(int lineNumber, String literal, Number number) {
        super(lineNumber, TokenType.NUMBER, literal);
        this.number = number;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public Number getNum() {
        return number;
    }

    @Override
    public Object value() {
        return number;
    }
}
