package rock.parser.element;

import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.lexer.Lexer;

import java.util.List;

public class MaybeElement extends NonTerminalElement {

    private Element element;

    public MaybeElement(Element element) {
        this.element = element;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        element.parse(lexer, res);
        return true;
    }
}
