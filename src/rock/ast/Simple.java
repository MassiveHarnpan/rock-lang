package rock.ast;

import rock.Environment;
import rock.Function;
import rock.RockException;
import rock.util.Logger;

import java.util.Arrays;

public class Simple extends ASTList {

    public Simple(ASTree... children) {
        super(children);
    }

    public ASTree expr() {
        return child(0);
    }

    public ASTree args() {
        return child(1);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        Object rst = expr().eval(env);
        if (rst instanceof Function) {
            Function func = (Function) rst;
            ASTree args = args();
            Object[] argarr = new Object[args.childCount()];
            for (int i = 0; i < argarr.length; i++) {
                argarr[i] = args.child(i).eval(env);
            }
            Logger.log("add args: " + Arrays.asList(argarr));
            return func.invoke(env, argarr);
        } else {
            return rst;
        }
    }
}
