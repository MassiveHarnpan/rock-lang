package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class BinaryParser extends NonTerminalParser {
    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        List<ASTree> list = new ArrayList<>();
        if (left.parse(lexer, list)
            && operator.parse(lexer, list)
            && right.parse(lexer, list)) {
            res.addAll(list);
            return true;
        }
        return false;
    }
//
//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return lexer.peek(0) != Token.EOF;
//    }


    private Parser left;
    private Parser operator;
    private Parser right;

    public BinaryParser(Class<ASTList> as, Parser left, Parser operator, Parser right) {
        super(as);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


}
