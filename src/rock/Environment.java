package rock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Environment upper;
    private Map<String, Object> values = new HashMap<>();

    public Environment(Environment upper) {
        this.upper = upper;
    }

    public Environment() {
    }

    public Object put(String key, Object value) {
        return values.put(key, value);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public Environment upper() {
        return upper;
    }

    public Environment lower() {
        Environment lower = new Environment();
        lower.upper = this;
        for (Map.Entry<String, Object> e : values.entrySet()) {
            lower.put(e.getKey(), e.getValue());
        }
        return lower;
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String input() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Environment output(String msg) {
        if (upper != null && upper != this) {
            upper.output(msg);
        } else {
            System.out.println(msg);
        }
        return this;
    }

}
