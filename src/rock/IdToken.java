package rock;

public class IdToken extends Token {
    private String text;

    public IdToken(int lineNumber, String literal, String text) {
        super(lineNumber, literal);
        this.text = text;
    }

    public IdToken(int lineNumber, String text) {
        super(lineNumber, text);
        this.text = text;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String getText() {
        return text;
    }
}
