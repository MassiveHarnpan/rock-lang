package rock;

import java.util.HashMap;
import java.util.Map;

public class Enviroument {

    private Map<String, Object> values = new HashMap<>();

    public Object put(String key, Object value) {
        return values.put(key, value);
    }

    public Object get(String key) {
        return values.get(key);
    }

}
