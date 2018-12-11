package rock.runtime;

import rock.data.*;
import rock.exception.RockException;

public class NativeEvaluator implements Evaluator {

    @Override
    public Rock eval(Environment env) throws RockException {
        return null;
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        return null;
    }

    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + ">";
    }
}
