package rock.runtime;

import rock.Environment;
import rock.Evaluator;
import rock.exception.RockException;

public class NativeEvaluator implements Evaluator {

    @Override
    public Object eval(Environment env) throws RockException {
        return null;
    }

    @Override
    public Object eval(Environment env, Object base) throws RockException {
        return null;
    }

    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + ">";
    }
}
