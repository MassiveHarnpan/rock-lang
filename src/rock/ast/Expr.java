package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.util.Logger;

import java.util.Iterator;

public class Expr extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Expr(elements);

    public Expr(ASTree... children) {
        super(children);
        liftSingleElement(true);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        Rock leftVal = child(0).eval(env);
        Logger.log("leftVal = " + leftVal);
        if (childCount() == 1) {
            return leftVal;
        }
        int index = 1;
        String msg;
        while (index < childCount() - 1) {
            String operator = ((ASTLeaf) child(index++)).token().literal();
            Rock rightVal = child(index++).eval(env);
            if (!leftVal.support(operator, rightVal)) {
                throw new UnsupportedOperationException(operator, leftVal.toString(), rightVal.toString());
            }
            msg = leftVal + " " + operator + " " + rightVal + " = ";
            leftVal = leftVal.compute(operator, rightVal);
            Logger.log(msg + leftVal);
        }
        return leftVal;
    }

    @Override
    public String toString() {
        if (childCount() == 1) {
            return child(0).toString();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.append(")").toString();
    }
}
