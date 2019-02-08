package rock.token;

import rock.exception.RockException;
import rock.util.Pos;

public class IntToken extends Token {

    private int value;

    public IntToken(Pos pos, String literal, int value) {
        super(pos, TokenType.INTEGER, literal);
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
