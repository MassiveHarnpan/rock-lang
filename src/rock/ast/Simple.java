package rock.ast;

import rock.data.Environment;
import rock.data.Function;
import rock.data.Rock;
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
    public Rock eval(Environment env) throws RockException {
        Rock func = expr().eval(env);
        ASTree args = args();
        Rock[] argarr = new Rock[args.childCount()];
        for (int i = 0; i < argarr.length; i++) {
            argarr[i] = args.child(i).eval(env);
        }
        Logger.log("add args: " + Arrays.asList(argarr));
        return func.invoke(argarr);
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
