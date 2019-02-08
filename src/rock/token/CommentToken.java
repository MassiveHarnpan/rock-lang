package rock.token;

import rock.util.Pos;

public class CommentToken extends Token {

    private String content;

    public CommentToken(Pos pos, String literal, String content) {
        super(pos, TokenType.COMMENT, literal);
        this.content = content;
    }

    @Override
    public Object value() {
        return content;
    }
}
