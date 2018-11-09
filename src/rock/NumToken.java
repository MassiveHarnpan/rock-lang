package rock;

public class NumToken extends Token {

    private Number number;

    public NumToken(int lineNumber, String literal, Number number) {
        super(lineNumber, literal);
        this.number = number;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int getNumber() {
        return number.intValue();
    }
}
