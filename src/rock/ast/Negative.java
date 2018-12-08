package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.RockDec;
import rock.data.RockInt;
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
    public Rock eval(Environment env) throws RockException {
        Object result = origin().eval(env).get();
        if (!(result instanceof Number)) {
            throw new UnsupportedOperationException("negative", result.toString());
        }
        Number num = (Number) result;
        Logger.log("negative.origin = " + num);
        if (result instanceof Integer || result instanceof Long) {
            return new RockInt(-num.intValue());
        } else {
            return new RockDec(-num.doubleValue());
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
