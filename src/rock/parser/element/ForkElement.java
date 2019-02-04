package rock.parser.element;

import rock.ast.ASTListFactory;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.ast.ASTree;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.List;

public class ForkElement extends NonTerminalElement {

    private List<Element> elements = new ArrayList<>();

    public ForkElement(ASTListFactory factory) {
        super(factory);
    }

    public ForkElement() {
    }

    public ForkElement or(Element element) {
        elements.add(element);
        return this;
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        for (Element e : elements) {
            if (e.parse(lexer, res)) {
                return true;
            }
        }
        return false;
    }
}
