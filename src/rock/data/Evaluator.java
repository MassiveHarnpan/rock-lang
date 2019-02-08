package rock.data;

import rock.exception.RockException;

public interface Evaluator {

    Rock eval(Environment env, Rock base) throws RockException;
    // value赋值给自己所代表位置
    Rock set(Environment env, Rock base, Rock value) throws RockException;
}
