package rock.lexer;

import rock.token.Token;

import java.io.Reader;

public abstract class Tokenizer {

    public Token next(LineReader reader) {
        int lineNumber = reader.getLineNumber();
        int lineOffset = reader.getLineOffset();
        Token token = read(reader);
        if (token == null) {
            reader.reset(lineNumber, lineOffset);
            return null;
        }
        return token;
    }

    protected abstract Token read(LineReader reader);

}
