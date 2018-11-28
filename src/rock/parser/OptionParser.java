package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.util.List;

public class OptionParser extends NonTerminalParser {

    private Parser parser;
    private boolean createAST;


    public OptionParser(Class<? extends ASTList> as, Parser parser, boolean createAST) {
        super(as);
        this.parser = parser;
        this.createAST = createAST;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        if (!parser.parse(lexer, res) && createAST) {
            res.add(create());
        }
        return true;
    }
}
