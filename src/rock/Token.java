package rock;

public class Token {
    public static final Token EOF = new Token(-1, "#EOF");
    public static final String EOL = "\n";


    private int lineNumber;
    private String literal;



    public Token(int lineNumber, String literal) {
        this.lineNumber = lineNumber;
        this.literal = literal;
    }

    public Token(int lineNumber) {
        this.lineNumber = lineNumber;
        this.literal = "";
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isIdentifier() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    // only for number token
    public int getNumber() throws RockException {
        throw new RockException("Not a number token");
    }

    // For Identifier or String Token
    public String getText() {
        return literal;
    }

    public String literal() {
        return literal;
    }

}
