package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
import rock.exception.RockException;

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
    public Rock eval(Environment env) throws RockException {
        ASTree cond =  condition();
        ASTree body = body();
        Rock result = null;
        while (!RockInteger.FALSE.equals(cond.eval(env))) {
            result = body.eval(env);
        }
        return result;
    }

    @Override
    public String toString(int indent, String space) {
        return RockString.repeat(indent, space) + "while " + condition() + " " + body().toString(indent, space);
    }
}
