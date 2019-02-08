package rock.token;

import rock.exception.RockException;

public class DecToken extends Token {

    private double value;

    public DecToken(int lineNumber, int offsetNumber, String literal, double value) {
        super(lineNumber, offsetNumber, TokenType.DECIMAL, literal);
        this.value = value;
    }

    @Override
    public double getDec() throws RockException {
        return value;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
