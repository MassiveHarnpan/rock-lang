package rock.ast;

import rock.Enviroument;

import java.util.List;

public class Expr extends ASTList {

    public Expr(ASTree... children) {
        super(children);
    }

    @Override
    public Object eval(Enviroument env) {
        Object leftVal = child(0).eval(env);
        if (childCount() == 1) {
            return leftVal;
        }
        int index = 1;
        while (index < childCount() - 1) {
            String operator = ((ASTLeaf) child(index)).token().literal();
            Object rightVal = child(++index).eval(env);
            leftVal = BinaryExpr.operate(leftVal, operator, rightVal);
        }
        return leftVal;
    }
}
