package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

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
    public Rock eval(Environment env) throws RockException {
        Object FALSE = Integer.valueOf(0);
        Object condResult = condition().eval(env);
        boolean match = condResult != null && !FALSE.equals(condResult);
        System.out.println(match);
        if (match) {
            return thenBlock().eval(env);
        } else {
            ASTree elseBlock = elseBlock();
            return elseBlock == null ? null : elseBlock.eval(env);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("if ").append(condition()).append(" ").append(thenBlock());
        if (childCount() > 2) {
            sb.append(" else ").append(elseBlock());
        }
        return sb.toString();
    }
}
