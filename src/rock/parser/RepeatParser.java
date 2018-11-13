package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;

public class RepeatParser extends NonTerminalParser {
    @Override
    public ASTree doParse(Lexer lexer) throws RockException {
        ASTList ast = new ASTList();
        ASTree elem = element.parse(lexer);
        if (elem == null) {
            return null;
        }
        ast.append(elem);
        while (lexer.peek(0) != Token.EOF) {
            int check = lexer.pointer();
            ASTree sep = separator.parse(lexer);
            if (sep == null) {
                break;
            }
            if (needSeparator) {
                ast.append(sep);
            }
            elem = element.parse(lexer);
            if (elem == null) {
                lexer.recovery(check);
                break;
            }
            ast.append(elem);
        }
        return ast;
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return lexer.peek(0) == Token.EOF || element.match(lexer);
//    }

    private Parser element;
    private Parser separator;
    private boolean needSeparator;

    public RepeatParser(Class<ASTList> as, Parser element, Parser separator, boolean needSeparator) {
        super(as);
        this.element = element;
        this.separator = separator;
        this.needSeparator = needSeparator;
    }

    public RepeatParser(Class<ASTList> as, Parser element, Parser separator) {
        super(as);
        this.element = element;
        this.separator = separator;
        this.needSeparator = true;
    }
}
