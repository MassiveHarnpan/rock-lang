package rock.parser.element;

import rock.ast.ASTListFactory;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.ast.ASTree;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceElement extends NonTerminalElement {

    private List<Element> elements = new ArrayList<>();

    public SequenceElement(ASTListFactory factory, Element... elements) {
        super(factory);
        this.elements.addAll(Arrays.asList(elements));
    }

    public SequenceElement(Element... elements) {
        super();
        this.elements.addAll(Arrays.asList(elements));
    }

    public SequenceElement then(Element e) {
        elements.add(e);
        return this;
    }

    public int count() {
        return elements.size();
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        for (Element e : elements) {
            if (!e.parse(lexer, res)) {
                return false;
            }
        }
        return true;
    }
}
