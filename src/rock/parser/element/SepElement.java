package rock.parser.element;

import rock.ast.ASTLeaf;
import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.token.Token;
import rock.token.TokenType;

import java.util.List;

public class SepElement extends TokenElement {
    public SepElement(Object... values) {
        super(TokenType.IDENTIFIER, values);
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        Token token = lexer.read();
        if (checkToken(token)) {
            return true;
        }
        return false;
    }
}
