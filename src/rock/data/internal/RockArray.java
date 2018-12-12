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
        return super.get(key);
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        if (key instanceof Integer) {
            return list.set((Integer) key, val);
        }
        return super.set(key, val);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
