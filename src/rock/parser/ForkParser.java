package rock.parser;

import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForkParser extends NonTerminalParser {

    private List<Parser> rules = new ArrayList<>();

    public ForkParser(Class<ASTList> as) {
        super(as);
    }

    public ForkParser(Class<ASTList> as, Parser... parsers) {
        super(as);
        rules.addAll(Arrays.asList(parsers));
    }

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        for (Parser rule : rules) {
            if (rule.parse(lexer, res)) {
                return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        for (Parser rule : rules) {
//            if (rule.match(lexer)) {
//                return true;
//            }
//        }
//        return false;
//    }



    public ForkParser or(Parser rule) {
        rules.add(rule);
        return this;
    }





}
