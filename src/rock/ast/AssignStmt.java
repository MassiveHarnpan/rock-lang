package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedASTException;
import rock.util.IndentationPrinter;
import rock.util.Logger;

public class AssignStmt extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new AssignStmt(elements);



    public AssignStmt(ASTree... children) {
        super(children);
    }

    public ASTree name() {
        return child(0);
    }

    public ASTree value() {
        return child(1);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        ASTree dest = name();
        Rock val = value().eval(env, null);
        Logger.log(dest + " = " + val);

        dest.set(env, null, val);
        return val;
    }

    @Override
    public void write(IndentationPrinter printer) {
        name().write(printer);
        printer.print(" = ");
        value().write(printer);
    }
}
