package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
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
        if (!RockInteger.FALSE.equals(condition().eval(env))) {
            return thenBlock().eval(env);
        } else {
            ASTree elseBlock = elseBlock();
            return elseBlock == null ? null : elseBlock.eval(env);
        }
    }

    @Override
    public String toString(int indent, String space) {
        StringBuffer sb = new StringBuffer();
        sb.append(RockString.repeat(indent, space)).append("if ").append(condition()).append(" ").append(thenBlock().toString(indent, space));
        if (childCount() > 2) {
            sb.append(" else ").append(elseBlock().toString(indent, space));
        }
        return sb.toString();
    }
}
