package rock.parser;

import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;

import java.util.*;

public class SeqParser extends NonTerminalParser {

    public SeqParser(Class<? extends ASTList> as) {
        super(as);
    }

    public SeqParser(Class<ASTList> as, Parser... parsers) {
        super(as);
        elements.addAll(Arrays.asList(parsers));
    }

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        for (Parser parser : elements) {
            if (!parser.parse(lexer, res)) {
                return false;
            }
        }
        return true;
    }

//    @Override
//    public boolean match(Lexer lexer) {
//        return false;
//    }

    private List<Parser> elements = new ArrayList<>();

    public SeqParser then(Parser... parsers) {
        elements.addAll(Arrays.asList(parsers));
        return this;
    }
}
