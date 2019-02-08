package rock.token;

import rock.util.Pos;

public class StrToken extends Token {

    private String str;

    public StrToken(Pos pos, String literal, String str) {
        super(pos, TokenType.STRING, literal);
        this.str = str;
    }

    @Override
    public String getStr() {
        return str;
    }

    @Override
    public Object value() {
        return str;
    }

    @Override
    public String toString() {
        return '\"' + str + '\"';
    }
}
