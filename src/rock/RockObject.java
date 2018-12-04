package rock;

import java.util.HashMap;
import java.util.Map;

public class RockObject {

    private RockClass clazz;

    public RockObject(RockClass clazz) {
        this.clazz = clazz;
    }

    private Map<String, Object> pool = new HashMap<>();


    public Object get(String name) {
        return pool.get(name);
    }

    public Object set(String name, Object obj) {
        pool.put(name, obj);
        return obj;
    }


}
