package rock.lexer;

import rock.token.Token;

import java.io.Reader;

public interface ILexer {

    Token read(int lineNumber, String s, int start);

}
