package rock.token;

public class CommentToken extends Token {

    private String content;

    public CommentToken(int lineNumber, int offsetNumber, String literal, String content) {
        super(lineNumber, offsetNumber, TokenType.COMMENT, literal);
        this.content = content;
    }

    @Override
    public Object value() {
        return content;
    }
}
