package rock.ast;

import rock.Environment;
import rock.Function;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
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
        if (!(rst instanceof Function)) {
            throw new UnsupportedOperationException("call function", rst.toString());
        }
        Function func = (Function) rst;
        ASTree args = args();
        Object[] argarr = new Object[args.childCount()];
        for (int i = 0; i < argarr.length; i++) {
            argarr[i] = args.child(i).eval(env);
        }
        Logger.log("add args: " + Arrays.asList(argarr));
        return func.invoke(env, argarr);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("# ").append(expr());
        ASTree args = args();
        if (args.childCount() > 0) {
            sb.append(args.child(0));
        }
        for (int i = 1; i < args.childCount(); i++) {
            sb.append(" ").append(child(i));
        }
        return sb.toString();
    }
}
