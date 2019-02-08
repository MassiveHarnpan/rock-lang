package rock.data;

import rock.exception.RockException;

public abstract class EvaluatorAdapter implements Evaluator {
    @Override
    public Rock set(Environment env, Rock base, Rock value) throws RockException {
        throw new RockException("cannot set " + value);
    }
}
