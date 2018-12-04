package rock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Environment outer;
    private Map<String, Object> values = new HashMap<>();

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

    public Object put(String key, Object value) {
        Environment env = locate(key);
        if (env == null) {
            env = this;
        }
        return env.values.put(key, value);
    }

    public Object get(String key) {
        Environment env = locate(key);
        if (env == null) {
            return null;
        }
        return env.values.get(key);
    }

    public Environment outer() {
        return outer;
    }

}
