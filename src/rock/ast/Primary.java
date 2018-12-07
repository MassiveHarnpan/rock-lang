package rock.ast;

import rock.Environment;
import rock.RockException;

public class Primary extends ASTList {

    public Primary(ASTree... children) {
        super(children);
        liftSingleElement(true);
    }

    public ASTree basic() {
        return child(0);
    }

    public int postfixCount() {
        return childCount() - 1;
    }

    public ASTree postfix(int nest) {
        return child(childCount() - nest - 1);
    }


    @Override
    public Object eval(Environment env) throws RockException {
        return evalSub(env, 0);
    }

    public Object evalSub(Environment env, int nest) throws RockException {
        if (nest < postfixCount()) {
            Object base = evalSub(env, nest + 1);
            ASTree postfix = postfix(nest);
            return postfix.eval(env, base);
        } else {
            return basic().eval(env);
        }
    }
}
