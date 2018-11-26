package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.util.ArrayList;
import java.util.List;

public class ASTParser extends NonTerminalParser {

    private Parser parser;

    public ASTParser(Class<ASTList> as, Parser parser) {
        super(as);
        this.parser = parser;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        List<ASTree> list = new ArrayList<>();
        if (!parser.parse(lexer, list)) {
            return false;
        }
        res.add(create(list.toArray(new ASTree[list.size()])));
        return true;
    }
}
