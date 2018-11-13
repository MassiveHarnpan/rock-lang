package rock.parser;

import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;

import java.util.ArrayList;
import java.util.List;

public class SeqParser extends NonTerminalParser {

    public SeqParser(Class<ASTList> as) {
        super(as);
    }

    @Override
    public ASTree doParse(Lexer lexer) {
        return null;
    }

//    @Override
//    public boolean match(Lexer lexer) {
//        return false;
//    }

    private List<Parser> elements = new ArrayList<>();

    public SeqParser then(Parser parser) {
        elements.add(parser);
        return this;
    }
}
