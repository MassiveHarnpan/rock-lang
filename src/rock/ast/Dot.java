package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedASTException;
import rock.util.IndentationPrinter;

public class Dot extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Dot(elements);

    public Dot(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }


    @Override
    public Rock set(Environment env, Rock base, Rock value) throws RockException {
        return base.setMember(name(), value);
    }

    @Override
    public String toString() {
        return "." + name();
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print(".").print(name());
    }
}
