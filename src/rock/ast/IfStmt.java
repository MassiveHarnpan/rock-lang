package rock.ast;

import rock.Enviroument;

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
    public Object eval(Enviroument env) {
        if (!Integer.valueOf(0).equals(condition().eval(env))) {
            return thenBlock();
        } else {
            ASTree elseBlock = elseBlock();
            return elseBlock == null ? null : elseBlock.eval(env);
        }
    }
}
