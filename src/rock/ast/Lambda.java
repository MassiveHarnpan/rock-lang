package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.exception.RockException;
import rock.util.Logger;

import java.util.Arrays;

public class Lambda extends ASTList {
    public Lambda(ASTree... children) {
        super(children);
    }

    public String[] params() {
        ASTree params = child(0);
        String[] paramNames = new String[params.childCount()];
        for (int i = 0; i < params.childCount(); i++) {
            paramNames[i] = ((Name) params.child(i)).name();
        }
        return paramNames;
    }

    public ASTree body() {
        return child(1);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        return new RockFunction(null, params(), body(), env);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String[] params = params();
        if (params.length == 0) {
            sb.append("()");
        } else if (params.length == 1) {
            sb.append(params[0]);
        } else {
            sb.append("(");
            sb.append(params[0]);
            for (int i = 1; i < params.length; i++) {
                sb.append(", ").append(params[i]);
            }
            sb.append(")");
        }
        return sb.append(" -> ").append(body()).toString();
    }
}
