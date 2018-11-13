package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;

import java.util.List;

public class TreeElement implements Element {

    protected Parser parser;

    public TreeElement(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        res.add(parser.parse(lexer));
    }

    @Override
    public boolean match(Lexer lexer) throws RockException {
        return parser.match(lexer);
    }
}
