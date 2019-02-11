package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockNil;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

public class Tertiary extends ASTList {
    public static final ASTListFactory FACTORY = elements -> new Tertiary(elements);

    public Tertiary(ASTree... children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }

    public ASTree elseBlock() {
        return child(2);
    }


    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        if (condition().eval(env, base).asBoolean()) {
            return thenBlock().eval(env, base);
        } else {
            return elseBlock().eval(env, base);
        }
    }



    @Override
    public void write(IndentationPrinter printer) {
        condition().write(printer);
        printer.print(" ? ");
        thenBlock().write(printer);
        printer.print(" : ");
        elseBlock().write(printer);
    }
}
