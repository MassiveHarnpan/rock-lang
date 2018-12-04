package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class RepeatParser extends NonTerminalParser {

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        while (lexer.peek(0) != Token.EOF) {
            int check = lexer.pointer();
            if (!element.parse(lexer, res)) {
                lexer.recovery(check);
                break;
            }
        }
        return true;
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return lexer.peek(0) == Token.EOF || element.match(lexer);
//    }

    private Parser element;

    public RepeatParser(Class<? extends ASTList> as, Parser element) {
        super(as);
        this.element = element;
    }

}
