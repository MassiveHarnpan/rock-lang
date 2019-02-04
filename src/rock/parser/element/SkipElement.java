package rock.parser.element;

import rock.ast.ASTList;
import rock.ast.ASTListFactory;
import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.lexer.Lexer;

import java.util.ArrayList;
import java.util.List;

public class SkipElement extends NonTerminalElement {

    private Element element;

    public SkipElement(Element element) {
        this(ASTList.FACTORY, element);
    }

    public SkipElement(ASTListFactory factory, Element element) {
        super(factory);
        this.element = element;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        List<ASTree> list = new ArrayList<>();
        return  (element.parse(lexer, list));
    }
}
