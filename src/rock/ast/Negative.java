package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockInteger;
import rock.data.internal.RockType;
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
        Rock origin = origin().eval(env);

        if (origin.type() == RockType.INT) {
            return new RockInteger(- (Integer) origin.getJavaPrototype());
        }
        if (origin.type() == RockType.DEC) {
            return new RockDecimal(- (Double) origin.getJavaPrototype());
        }

        throw new UnsupportedOperationException("negative", origin.toString());
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
