package rock;

import rock.exception.RockException;

import java.util.HashMap;
import java.util.Map;

public class RockObject extends Environment {

    private RockClass clazz;

    public RockObject(RockClass clazz, Environment outer) {
        super(outer);
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<object:").append(" instanceof ").append(clazz.name());
        return sb.append(">").toString();
    }
}
