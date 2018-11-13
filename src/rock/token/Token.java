package rock.token;

import rock.RockException;

public class Token {
    public static final Token EOF = new Token(-1, "#EOF");
    public static final String EOL = "\n";


    private int lineNumber;
    private String literal;
    private TokenType type = null;


    public Token(int lineNumber, TokenType type, String literal) {
        this.lineNumber = lineNumber;
        this.type = type;
        this.literal = literal;
    }

    public Token(int lineNumber, String literal) {
        this(lineNumber, null, literal);
    }

    public Token(int lineNumber, TokenType type) {
        this(lineNumber, type, "");
    }

    public Token(int lineNumber) {
        this(lineNumber, null, "");
    }






    public TokenType type() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isIdentifier() {
        return type == TokenType.IDENTIFIER;
    }

    public boolean isNumber() {
        return type == TokenType.NUMBER;
    }

    public boolean isString() {
        return type == TokenType.STRING;
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
}
