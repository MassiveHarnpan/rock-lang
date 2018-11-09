package rock;

public class StrToken extends Token {

    private String text;

    public StrToken(int lineNumber, String literal, String text) {
        super(lineNumber, literal);
        this.text = text;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getText() {
        return text;
    }
}
