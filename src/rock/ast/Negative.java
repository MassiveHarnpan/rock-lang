package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.util.Logger;

public class Negative extends ASTList {

    public Negative(ASTree... children) {
        super(children);
    }

    public ASTree origin() {
        return child(0);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        Object result = origin().eval(env);
        if (!(result instanceof Number)) {
            throw new UnsupportedOperationException("negative", result.toString());
        }
        Number num = (Number) result;
        Logger.log("negative.origin = " + num);
        if (result instanceof Integer || result instanceof Long) {
            return -num.intValue();
        } else {
            return -num.doubleValue();
        }
    }

    @Override
    public ASTree simplify() {
        return this;
    }

    @Override
    public String toString() {
        return "-" + origin();
    }
}
