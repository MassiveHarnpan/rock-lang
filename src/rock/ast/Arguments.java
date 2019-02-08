package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

import java.util.Iterator;

public class Arguments extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Arguments(elements);

    public Arguments(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock[] args = new Rock[childCount()];
        for (int i = 0; i < childCount(); i++) {
            args[i] = child(i).eval(env, null);
        }
        return base.invoke(args);
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print("(");
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            itr.next().write(printer);
            if (itr.hasNext()) {
                printer.print(", ");
            }
        }
        printer.print(")");
    }
}
