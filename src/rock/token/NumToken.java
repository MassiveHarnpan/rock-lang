package rock.token;

public class NumToken extends Token {

    private Number number;

    public NumToken(int lineNumber, int offsetNumber, String literal, Number number) {
        super(lineNumber, offsetNumber, TokenType.NUMBER, literal);
        this.number = number;
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
