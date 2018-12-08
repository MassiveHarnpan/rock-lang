package rock.parser;

import rock.Lexer;
import rock.exception.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.util.List;

public class ASTParser extends NonTerminalParser {

    private Parser parser;

    public ASTParser(Class<ASTList> as, Parser parser) {
        super(as);
        this.parser = parser;
    }

    public ASTParser(Class<ASTList> as) {
        super(as);
    }

    public ASTParser of(Parser parser) {
        this.parser = parser;
        return this;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        ASTree ast = parser.parse(lexer);
        if (ast == null) {
            return false;
        }
        res.add(ast);
        return true;
    }
}
