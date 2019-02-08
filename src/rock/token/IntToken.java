package rock.token;

import rock.exception.RockException;

public class IntToken extends Token {

    private int value;

    public IntToken(int lineNumber, int offsetNumber, String literal, int value) {
        super(lineNumber, offsetNumber, TokenType.INTEGER, literal);
        this.value = value;
    }

    @Override
    public int getInt() throws RockException {
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
