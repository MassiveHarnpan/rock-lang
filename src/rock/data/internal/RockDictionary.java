package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

public class RockDictionary extends Rock {
    @Override
    public Rock member(String mem) throws RockException {
        throw new RockException("cannot get member of a dictionary: "+ toString());
    }

    @Override
    public Rock member(String mem, Rock value) throws RockException {
        throw new RockException("cannot set member of a dictionary: "+ toString());
    }

    @Override
    public boolean support(String op, Rock another) {
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        return null;
    }

    @Override
    public Environment env() throws RockException {
        return null;
    }

    @Override
    public RockType type() {
        return RockType.DIC;
    }

    @Override
    public boolean hasJavaPrototype() {
        return false;
    }

    @Override
    public Rock get(Object key) throws RockException {
        return null;
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        return null;
    }
}
