package rock.ast;

import rock.Environment;
import rock.RockException;

public class WhileStmt extends ASTList {

    public WhileStmt(ASTree... children) {
        super(children);
    }



    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }


    @Override
    public Object eval(Environment env) throws RockException {
        ASTree cond =  condition();
        ASTree body = body();
        Object result = null;
        while (!Integer.valueOf(0).equals(cond.eval(env))) {
            result = body.eval(env);
        }
        return result;
    }
}
