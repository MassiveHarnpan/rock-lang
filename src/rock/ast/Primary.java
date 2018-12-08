package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.Iterator;

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
    public Rock eval(Environment env) throws RockException {
        return evalSub(env, 0);
    }

    public Rock evalSub(Environment env, int nest) throws RockException {
        if (nest < postfixCount()) {
            Rock base = evalSub(env, nest + 1);
            ASTree postfix = postfix(nest);
            return postfix.eval(env, base);
        } else {
            return basic().eval(env);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
        }
        return sb.toString();
    }
}
