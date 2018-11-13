package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.util.List;

public class RepeatElement implements Element {

    protected Parser parser;
    protected boolean onlyOnce;

    public RepeatElement(Parser p, boolean once) {
        parser = p; onlyOnce = once;
    }

    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        while (parser.match(lexer)) {
            ASTree ast = parser.parse(lexer);
            if (ast.getClass() != ASTList.class || ast.childCount() > 0) {
                res.add(ast);
            }
            if (onlyOnce) {
                break;
            }
        }
    }

    @Override
    public boolean match(Lexer lexer) throws RockException {
        return parser.match(lexer);
    }
}
