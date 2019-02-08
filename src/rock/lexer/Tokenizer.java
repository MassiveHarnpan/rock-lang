package rock.lexer;

import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;

public abstract class Tokenizer {

    public Token next(LineReader reader) {
        if (!reader.hasMore()) {
            return null;
        }
        Pos cp = reader.checkPoint();
        Token token = read(reader, cp);
        if (token == null) {
            reader.reset(cp);
            return null;
        }
        return token;
    }

    protected abstract Token read(LineReader reader, Pos start);

}
