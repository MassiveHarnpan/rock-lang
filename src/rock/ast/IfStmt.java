package rock.ast;

import rock.Environment;
import rock.RockException;

public class IfStmt extends ASTList {

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
    public Object eval(Environment env) throws RockException {
        Object FALSE = Integer.valueOf(0);
        Object condResult = condition().eval(env);
        boolean match = !FALSE.equals(condResult);
        System.out.println(match);
        if (match) {
            return thenBlock().eval(env);
        } else {
            ASTree elseBlock = elseBlock();
            return elseBlock == null ? null : elseBlock.eval(env);
        }
    }
}
