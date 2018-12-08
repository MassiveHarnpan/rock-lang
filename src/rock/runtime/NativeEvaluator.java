package rock.runtime;

import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.Rock;
import rock.exception.RockException;

public class NativeEvaluator implements Evaluator {

    @Override
    public Rock eval(Environment env) throws RockException {
        return null;
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        return null;
    }

    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + ">";
    }
}
