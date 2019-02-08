package rock.data.internal;

import rock.ast.ASTree;
import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RockDictionary extends RockAdapter {

    private Map<Object, Rock> values = new HashMap<>();

    @Override
    public Rock getMember(String mem) throws RockException {
        throw new RockException("cannot get setMember of a dictionary: "+ toString());
    }

    @Override
    public Rock setMember(String mem, Rock value) throws RockException {
        throw new RockException("cannot set setMember of a dictionary: "+ toString());
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
        if (values.containsKey(key)) {
            return values.get(key);
        }
        throw new RockException("Cannot find key: " + key);
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        return values.put(key, val);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        Iterator<Map.Entry<Object, Rock>> itr = values.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Object, Rock> e = itr.next();
            sb.append(e.getKey()).append(":").append(e.getValue());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("}").toString();
    }
}
