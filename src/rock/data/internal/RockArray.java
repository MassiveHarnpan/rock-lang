package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RockArray extends RockAdapter {

    private List<Rock> list = new ArrayList<>();

    private RockFunction remover;
    private RockFunction adder;
    private RockFunction setter;
    private RockFunction getter;
    private RockFunction size;


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
    public Rock member(String mem) throws RockException {
        switch (mem) {
            case "length": return new RockInteger(list.size());
            case "get": return getter;
            case "set": return setter;
            case "size": return size;
            case "add": return adder;
            case "remove": return remover;
        }
        throw new RockException("cannot get member: " + mem);
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
