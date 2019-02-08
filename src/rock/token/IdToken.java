package rock.token;

import rock.util.Pos;

public class IdToken extends Token {
    private String text;

    public IdToken(Pos pos, String text) {
        super(pos, TokenType.IDENTIFIER, text);
        this.text = text;
    }

    @Override
    public String getStr() {
        return text;
    }

    @Override
    public Object value() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
