package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockInteger;
import rock.data.internal.RockNil;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

public class IfStmt extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new IfStmt(elements);

    public IfStmt(ASTree... children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }

    public ASTree elseBlock() {
        return childCount() > 2 ? child(2) : null;
    }


    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        if (condition().eval(env, base).asBoolean()) {
            return thenBlock().eval(env, base);
        } else {
            ASTree elseBlock = elseBlock();
            return elseBlock == null ? RockNil.INSTANCE : elseBlock.eval(env, base);
        }
    }



    @Override
    public void write(IndentationPrinter printer) {
        printer.print("if (");
        condition().write(printer);
        printer.print(") ");
        thenBlock().write(printer);
        ASTree elseBlock = elseBlock();
        if (elseBlock != null) {
            printer.print(" else ");
            elseBlock.write(printer);
        }
    }
}
