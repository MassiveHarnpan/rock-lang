package rock.parser.element;

import rock.ast.ASTList;
import rock.ast.ASTListFactory;
import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.lexer.Lexer;

import java.util.ArrayList;
import java.util.List;

public abstract class NonTerminalElement extends Element {

    private ASTListFactory factory;

    public NonTerminalElement(ASTListFactory factory) {
        this.factory = factory;
    }

    public NonTerminalElement() {
        this.factory = ASTList.FACTORY;
    }

    public ASTListFactory getFactory() {
        return factory;
    }

    @Override
    public ASTree parse(Lexer lexer) throws ParseException {
        List<ASTree> res = new ArrayList<>();
        if (parse(lexer, res)) {
            return factory.create(res.toArray(new ASTree[res.size()]));
        }
        return null;
    }
}
