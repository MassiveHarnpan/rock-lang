package rock.token;

import rock.exception.RockException;
import rock.util.Pos;

public class DecToken extends Token {

    private double value;

    public DecToken(Pos pos, String literal, double value) {
        super(pos, TokenType.DECIMAL, literal);
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
