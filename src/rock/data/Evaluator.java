package rock.data;

import rock.exception.RockException;

public interface Evaluator {
    Rock eval(Environment env) throws RockException;
    Proxy proxy(Environment env, Rock base) throws RockException;
}
