package rock.parser.element;

import rock.ast.ASTLeaf;
import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.token.Token;
import rock.token.TokenType;

import java.util.*;

public class TokenElement extends Element {

    private TokenType type;
    private Set values;

    public TokenElement(TokenType type, Object... values) {
        this.type = type;
        if (values.length == 0) {
            this.values = null;
        } else {
            this.values = new HashSet(Arrays.asList(values));
        }
    }

    @Override
    public ASTree parse(Lexer lexer) throws ParseException {
        Token token = lexer.peek(0);
        if (checkToken(token)) {
            lexer.read();
            return new ASTLeaf(token);
        }
        return null;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        Token token = lexer.read();
        if (checkToken(token)) {
            res.add(new ASTLeaf(token));
            return true;
        }
        return false;
    }

    public boolean checkToken(Token token) {
        if (token.type() != type) {
            return false;
        }
        if (values == null) {
            return true;
        }
        if (values.contains(token.value())) {
            return true;
        }
        return false;
    }
}
