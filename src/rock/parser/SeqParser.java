package rock.parser;

import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;

import java.util.*;

public class SeqParser extends NonTerminalParser {

    public SeqParser(Class<ASTList> as) {
        super(as);
    }

    public SeqParser(Class<ASTList> as, Parser... parsers) {
        super(as);
        elements.addAll(Arrays.asList(parsers));
    }

    @Override
    public ASTree doParse(Lexer lexer) throws RockException {
        List<ASTree> list = new ArrayList<>();
        for (Parser parser : elements) {
            ASTree ast = parser.parse(lexer);
            if (ast == null) {
                return null;
            }
            if (needs.contains(parser)) {
                list.add(ast);
            }
        }
        return create(list.toArray(new ASTree[list.size()]));
    }

//    @Override
//    public boolean match(Lexer lexer) {
//        return false;
//    }

    private List<Parser> elements = new ArrayList<>();
    private Set<Parser> needs = new HashSet<>();

    public SeqParser then(Parser parser) {
        elements.add(parser);
        needs.add(parser);
        return this;
    }

    public SeqParser skip(Parser parser) {
        elements.add(parser);
        return this;
    }

    public SeqParser then(Parser parser, boolean need) {
        elements.add(parser);
        if (need) {
            needs.add(parser);
        }
        return this;
    }
}
