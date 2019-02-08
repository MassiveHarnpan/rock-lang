package rock.token;

import rock.util.Pos;

public class NameToken extends Token {

    public NameToken(Pos pos, String name) {
        super(pos, TokenType.NAME, name);
    }


    @Override
    public Object value() {
        return literal();
    }

    @Override
    public String toString() {
        return literal();
    }
}
