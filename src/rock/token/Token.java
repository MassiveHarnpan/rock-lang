package rock.token;

import rock.exception.RockException;
import rock.util.Pos;

public class Token {
    public static final Token EOF = new Token(new Pos("$ANY$", -1, -1), TokenType.EOF, "#EOF");
    public static final String EOL = "\n";


    private Pos pos;
    private String literal;
    private TokenType type = null;


    public Token(Pos pos, TokenType type, String literal) {
        this.pos = pos;
        this.type = type;
        this.literal = literal;
    }






    public TokenType type() {
        return type;
    }

    public Pos getPos() {
        return pos;
    }

    public int getInt() throws RockException {
        throw new RockException("Not a integer token");
    }

    public double getDec() throws RockException {
        throw new RockException("Not a decimal token");
    }

    public String getStr() {
        return literal;
    }

    public String literal() {
        return literal;
    }

    public Object value() {
        return null;
    }

    @Override
    public String toString() {
        //return "{type=" + getClass().getSimpleName() + ", literal=" + literal + ", value=" + value() + "}";
        return String.valueOf(value());
    }
}
