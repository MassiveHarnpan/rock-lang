package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

public class WhileStmt extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new WhileStmt(elements);

    public WhileStmt(ASTree... children) {
        super(children);
    }



    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }


    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        ASTree cond =  condition();
        ASTree body = body();
        Rock result = null;
        while (cond.eval(env, base).asBoolean()) {
            result = body.eval(env, base);
        }
        return result;
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print("while (");
        condition().write(printer);
        printer.print(") ");
        body().write(printer);
    }
}
