package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockAdapter extends Rock {
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
        return null;
    }

    @Override
    public boolean hasJavaPrototype() {
        return false;
    }

    @Override
    public Object getJavaPrototype() {
        return null;
    }

    @Override
    public Rock get(Object key) throws RockException {
        throw new UnsupportedOperationException("get", this.toString(), key.toString());
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        throw new UnsupportedOperationException("set", this.toString(), key.toString(), val.toString());
    }
}