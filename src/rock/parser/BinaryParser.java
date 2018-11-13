package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;

public class BinaryParser extends NonTerminalParser {
    @Override
    public ASTree doParse(Lexer lexer) throws RockException {
        ASTree left, operator, right;
        if ((left = this.left.parse(lexer)) == null
            || (operator = this.operator.parse(lexer)) == null
            || (right = this.right.parse(lexer)) == null) {
            return null;
        }
        ASTList ast = create(left, operator, right);
        return ast;
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
