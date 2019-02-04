package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

import java.util.Iterator;

public class Primary extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Primary(elements);

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

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        Environment b = env;
        if (base != null) {
            b = base;
        }
        if (postfixCount() == 0) {
            ASTree basic = basic();
            if (!(basic instanceof Name)) {
                throw new UnsupportedOperationException("proxy", this.toString(), basic.toString());
            }
            return new EnvProxy(b, ((Name) basic).name());
        }
        ASTree last = postfix(0);
        Rock rock = evalSub(b,1);
        return last.proxy(env, rock);
    }

    public Rock evalSub(Environment env, int nest) throws RockException {
        if (nest < postfixCount()) {
            Rock base = evalSub(env, nest + 1);
            ASTree postfix = postfix(nest);
            return postfix.proxy(env, base).get();
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
