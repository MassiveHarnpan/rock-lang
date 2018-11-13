package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTree;
import rock.token.TokenType;

import java.util.List;
import java.util.Set;

public class SkipElement extends TokenElement {

    public SkipElement(Class<ASTLeaf> clazz, TokenType type, Object... values) {
        super(clazz, type, values);
    }

    public SkipElement(TokenType type, Object... values) {
        super(null, type, values);
    }

    public SkipElement(Object... values) {
        super(null, TokenType.NAME, values);
    }

    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        lexer.read();
    }
}
