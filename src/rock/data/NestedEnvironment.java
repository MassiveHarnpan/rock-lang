package rock.data;

import rock.exception.RockException;

import java.util.HashMap;
import java.util.Map;

public class NestedEnvironment implements Environment {

    private Environment outer;
    protected Map<Object, Rock> values = new HashMap<>();

    public NestedEnvironment(Environment outer) {
        this.outer = outer;
    }

    public NestedEnvironment() {
    }

    protected NestedEnvironment locate(Object key) {
        if (values.containsKey(key)) {
            return this;
        }
        if (outer == null) {
            return null;
        }
        return outer instanceof NestedEnvironment ? ((NestedEnvironment) outer).locate(key) : null;
    }

    @Override
    public Rock set(Object key, Rock value) {
        NestedEnvironment env = locate(key);
        if (env == null) {
            env = this;
        }
        return env.values.put(key, value);
    }

    @Override
    public Rock get(Object key) throws RockException {
        NestedEnvironment env = locate(key);
        if (env == null) {
            return null;
        }
        return env.values.get(key);
    }

    public Environment outer() {
        return outer;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
