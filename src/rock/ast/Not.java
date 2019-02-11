package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockBoolean;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockInteger;
import rock.data.internal.RockType;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.util.IndentationPrinter;

import java.util.ArrayList;
import java.util.List;

public class Not extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Not(elements);


    public Not(ASTree... elements) {
        super(elements);
    }

    public ASTree origin() {
        return child(0);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock origin = origin().eval(env, base);
        return RockBoolean.valueOf(!origin.asBoolean());
    }

    @Override
    public ASTree simplify() {
        return this;
    }

    @Override
    public String toString() {
        return "!" + origin();
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print("!");
        origin().write(printer);
    }
}
