package rock.parser.element;

import rock.ast.ASTList;
import rock.ast.ASTListFactory;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.ast.ASTree;
import rock.exception.RockException;
import rock.token.Token;

import java.util.List;

public class RepeatElement extends NonTerminalElement {

    private Element element;
    private Element splitter;

    private boolean atLeastOneElement = true;

    public RepeatElement(Element element, Element splitter, ASTListFactory factory) {
        super(factory);
        this.element = element;
        this.splitter = splitter;
    }

    public RepeatElement(Element element, Element splitter) {
        super(ASTList.FACTORY);
        this.element = element;
        this.splitter = splitter;
    }

    public boolean isAtLeastOneElement() {
        return atLeastOneElement;
    }

    public void setAtLeastOneElement(boolean atLeastOneElement) {
        this.atLeastOneElement = atLeastOneElement;
    }

    // At least one element
    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {

        int check = lexer.pointer();
        int back = res.size();

        if (!element.parse(lexer, res)) {
            if (atLeastOneElement) {
                return false;
            } else {
                lexer.recovery(check);
                while (res.size() > back) {
                    res.remove(res.size() - 1);
                }
                return true;
            }
        }

        while (lexer.peek(0) != Token.EOF) {
            check = lexer.pointer();
            back = res.size();

            if (splitter != null && !splitter.parse(lexer, res)) {
                lexer.recovery(check);
                while (res.size() > back) {
                    res.remove(res.size() - 1);
                }
                break;
            }

            if (!element.parse(lexer, res)) {
                lexer.recovery(check);
                while (res.size() > back) {
                    res.remove(res.size() - 1);
                }
                break;
            }
        }
        return true;
    }
}
