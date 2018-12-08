package rock.data;

import rock.exception.RockException;
import rock.util.Logger;

public class RockObject extends Rock {

    public static final String INVOKE = "__invoke__";
    public static final String GET = "__get__";
    public static final String SET = "__set__";
    public static final String COMPUTE = "__compute__";

    private RockClass clazz;
    private Environment env;

    public RockObject(RockClass clazz, Environment outer) {
        this.clazz = clazz;
        env = new Environment(outer);
    }

    @Override
    public Object get() throws RockException {
        return this;
    }

    @Override
    public Rock get(String key) throws RockException {
        Rock func = env.get(GET);
        if (func != null) {
            return func.invoke(new RockStr(key));
        }
        return env.get(key);
    }

    @Override
    public Rock set(String key, Rock val) throws RockException {
        Rock func = env.get(SET);
        if (func != null) {
            return func.invoke(new RockStr(key), val);
        }
        return env.set(key, val);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        Rock func = env.get(COMPUTE);
        if (func != null) {
            return func.invoke(new RockStr(op), another);
        }
        return super.compute(op, another);
    }

    @Override
    public Rock invoke(Rock... args) throws RockException {
        Rock func = env.get(INVOKE);
        if (func != null) {
            return func.invoke(args);
        }
        return super.invoke(args);
    }

    @Override
    public Environment env() throws RockException {
        return env;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<object:").append(" instanceof ").append(clazz.name());
        return sb.append(">").toString();
    }
}
