package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.runtime.NativeEvaluator;

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

    public RockArray(Rock... elements) {
        super();
        list.addAll(Arrays.asList(elements));
        remover = new RockFunction("remove", new String[]{"index"}, new NativeEvaluator(){
            @Override
            public Rock eval(Environment env) throws RockException {
                Rock index = env.get("index");
                if (index != null && index.type() == RockType.INT) {
                    int i = (int) index.getJavaPrototype();
                    if (i < 0 || i >= list.size()) {
                        throw new RockException("index out of boundary: " + i + " of " + list.size());
                    }
                    Rock r = list.get(i);
                    list.remove(i);
                    return r;
                }
                throw new RockException("index must be a integer: " + index);
            }
        }, null);
        adder = new RockFunction("add", new String[]{"value"}, new NativeEvaluator(){
            @Override
            public Rock eval(Environment env) throws RockException {
                Rock value = env.get("value");
                list.add(value);
                return value;
            }
        }, null);
        setter = new RockFunction("set", new String[]{"index", "value"}, new NativeEvaluator(){
            @Override
            public Rock eval(Environment env) throws RockException {
                Rock index = env.get("index");
                Rock value = env.get("value");
                if (index != null && index.type() == RockType.INT) {
                    int i = (int) index.getJavaPrototype();
                    if (i < 0 || i >= list.size()) {
                        throw new RockException("index out of boundary: " + i + " of " + list.size());
                    }
                    list.set(i, value);
                    return value;
                }
                throw new RockException("index must be a integer: " + index);
            }
        }, null);
        getter = new RockFunction("get", new String[]{"index"}, new NativeEvaluator(){
            @Override
            public Rock eval(Environment env) throws RockException {
                Rock index = env.get("index");
                if (index != null && index.type() == RockType.INT) {
                    int i = (int) index.getJavaPrototype();
                    if (i < 0 || i >= list.size()) {
                        throw new RockException("index out of boundary: " + i + " of " + list.size());
                    }
                    Rock r = list.get(i);
                    return r;
                }
                throw new RockException("index must be a integer: " + index);
            }
        }, null);
        size = new RockFunction("size", new String[0], new NativeEvaluator(){
            @Override
            public Rock eval(Environment env) throws RockException {
                return new RockInteger(list.size());
            }
        }, null);
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
