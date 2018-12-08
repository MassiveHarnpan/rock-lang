package rock.data;

import rock.exception.RockException;

public interface Evaluator {
    Rock eval(Environment env) throws RockException;
    Rock eval(Environment env, Rock base) throws RockException;
}
