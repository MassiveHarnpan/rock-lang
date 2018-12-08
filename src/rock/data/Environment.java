package rock.data;

import rock.exception.RockException;

import java.util.HashMap;
import java.util.Map;

public class Environment implements GetterAndSetter {

    private Environment outer;
    protected Map<String, Rock> values = new HashMap<>();

    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Environment() {
    }

    protected Environment locate(String key) {
        if (values.containsKey(key)) {
            return this;
        }
        if (outer == null) {
            return null;
        }
        return outer.locate(key);
    }

    @Override
    public Rock set(String key, Rock value) {
        Environment env = locate(key);
        if (env == null) {
            env = this;
        }
        return env.values.put(key, value);
    }

    @Override
    public Rock get(String key) throws RockException {
        Environment env = locate(key);
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
