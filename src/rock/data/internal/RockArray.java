package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.List;

public class RockArray extends RockAdapter {

    private List<Rock> list = new ArrayList<>();


    @Override
    public Rock compute(String op, Rock another) {
        return null;
    }

    @Override
    public Environment env() throws RockException {
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
        return null;
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        return null;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
