package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.util.Logger;

import java.util.Iterator;

public class Expr extends ASTList {

    public Expr(ASTree... children) {
        super(children);
        liftSingleElement(true);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        Object leftVal = child(0).eval(env);
        if (childCount() == 1) {
            Logger.log(leftVal + " = " + leftVal);
            return leftVal;
        }
        int index = 1;
        String msg;
        while (index < childCount() - 1) {
            String operator = ((ASTLeaf) child(index++)).token().literal();
            Object rightVal = child(index++).eval(env);
            msg = leftVal + " " + operator + " " + rightVal + " = ";
            leftVal = BinaryExpr.operate(leftVal, operator, rightVal);
            Logger.log(msg + leftVal);
        }
        return leftVal;
    }

    @Override
    public String toString() {
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
