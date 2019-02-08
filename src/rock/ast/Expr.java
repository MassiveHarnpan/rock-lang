package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.util.IndentationPrinter;
import rock.util.Logger;
import rock.util.Operators;

import java.util.Iterator;

public class Expr extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Expr(elements);

    public Expr(ASTree... children) {
        super(children);
        liftSingleElement(true);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock leftVal = child(0).eval(env, base);
        Logger.log("leftVal = " + leftVal);
        if (childCount() == 1) {
            return leftVal;
        }
        int index = 1;
        String msg;
        while (index < childCount() - 1) {
            String operator = child(index++).token().literal();
            Rock rightVal = child(index++).eval(env, base);
            msg = leftVal + " " + operator + " " + rightVal + " = ";
            leftVal = Operators.compute(leftVal, operator, rightVal);
            Logger.log(msg + leftVal);
        }
        return leftVal;
    }

    @Override
    public void write(IndentationPrinter printer) {
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            itr.next().write(printer);
            if (itr.hasNext()) {
                printer.print(" ");
            }
        }
    }
}
