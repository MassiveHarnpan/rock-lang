package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;

import java.util.List;

public class EndElement implements Element {
    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        return;
    }

    @Override
    public boolean match(Lexer lexer) throws RockException {
        return true;
    }
}
