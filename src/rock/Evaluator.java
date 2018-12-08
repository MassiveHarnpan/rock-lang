package rock;

import rock.exception.RockException;

public interface Evaluator {
    Object eval(Environment env) throws RockException;
    Object eval(Environment env, Object base) throws RockException;
}
