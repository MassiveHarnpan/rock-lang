package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.util.List;

public class OptionParser extends NonTerminalParser {

    private Parser parser;


    public OptionParser(Class<? extends ASTList> as, Parser parser) {
        super(as);
        this.parser = parser;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        parser.parse(lexer, res);
        return true;
    }
}
