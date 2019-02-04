package rock.token;

import rock.exception.RockException;

public class Token {
    public static final Token EOF = new Token(-1, -1, TokenType.EOF, "#EOF");
    public static final String EOL = "\n";


    private int lineNumber;
    private int offsetNumber;
    private String literal;
    private TokenType type = null;


    public Token(int lineNumber, int offsetNumber, TokenType type, String literal) {
        this.lineNumber = lineNumber;
        this.offsetNumber = offsetNumber;
        this.type = type;
        this.literal = literal;
    }






    public TokenType type() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getOffsetNumber() {
        return offsetNumber;
    }

    // only for number token
    public Number getNum() throws RockException {
        throw new RockException("Not a number token");
    }

    // For Identifier or String Token
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
        return "{type=" + getClass().getSimpleName() + ", literal=" + literal + ", value=" + value() + "}";
    }
}
