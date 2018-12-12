package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockString;
import rock.exception.RockException;
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
    public String toString(int indent, String space) {
        StringBuffer sb = new StringBuffer();
        sb.append(RockString.repeat(indent, space)).append("# ").append(expr());
        ASTree args = args();
        for (int i = 0; i < args.childCount(); i++) {
            sb.append(" ").append(args.child(i).toString());
        }
        return sb.toString();
    }
}
