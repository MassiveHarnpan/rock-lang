package rock.lexer;

import rock.token.Token;

import java.io.Reader;

public interface ILexer {

    Token lex(int lineNumber, String s, int start);

}
