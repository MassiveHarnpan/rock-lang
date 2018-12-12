package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RockArray extends RockAdapter {

    private List<Rock> list = new ArrayList<>();

    public RockArray(Rock... elements) {
        list.addAll(Arrays.asList(elements));
    }

    @Override
    public RockType type() {
        return RockType.ARR;
    }

    @Override
    public boolean support(String op, Rock another) {
        if ("+".equals(op) || "-".equals(op)) {
            return true;
        }
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        RockArray rst = new RockArray(list.toArray(new Rock[list.size()]));
        if ("+".equals(op)) {
            if (another.type() == RockType.ARR) {
                RockArray arr = (RockArray) another;
                rst.list.addAll(arr.list);
                return rst;
            }
            rst.list.add(another);
            return rst;
        }
        if ("-".equals(op)) {
            if (another.type() == RockType.ARR) {
                RockArray arr = (RockArray) another;
                for (Rock rock : arr.list) {
                    rst.list.remove(rock);
                }
                return rst;
            }
            rst.list.remove(another);
            return rst;
        }
        return super.compute(op, another);
    }

    @Override
    public Environment env() throws RockException {
        return null;
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return list;
    }

    @Override
    public Rock get(Object key) throws RockException {
        if (key instanceof Integer) {
            return list.get((Integer) key);
        }
        throw new RockException("index must be an integer: " + key);
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        if (key instanceof Integer) {
            int index = (int) key;
            if (index < 0 || index > list.size()) {
                throw new RockException("out of boundary: " + index + " of size " + list.size());
            }
            if (index == list.size()) {
                list.add(val);
            } else {
                list.set(index, val);
            }
            return val;
        }
        throw new RockException("index must be an integer: " + key);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
