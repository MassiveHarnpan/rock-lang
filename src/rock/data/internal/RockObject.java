package rock.data.internal;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;

public class RockObject extends RockAdapter {

    public static final String INVOKE = "__invoke__";
    public static final String NEW = "__new__";
    public static final String GET = "__get__";
    public static final String SET = "__set__";
    public static final String COMPUTE = "__compute__";

    private RockClass clazz;
    private NestedEnvironment env;

    public RockObject(RockClass clazz, Environment outer) {
        this.clazz = clazz;
        env = new NestedEnvironment(outer);
    }

    @Override
    public RockType type() {
        return RockType.OBJ;
    }

    @Override
    public Rock getMember(String mem) throws RockException {
        Environment e = env.locate(mem);
        if (e != env) {
            throw new RockException("cannot get setMember: " + mem);
        }
        return env.localGet(mem);
    }

    @Override
    public Rock setMember(String mem, Rock value) throws RockException {
        Environment e = env.locate(mem);
        if (e != env) {
            throw new RockException("cannot get setMember: " + mem);
        }
        return env.localSet(mem, value);
    }

    @Override
    public boolean asBoolean() throws RockException {
        return true;
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
    public NestedEnvironment env() throws RockException {
        return env;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<object:").append(" instanceof ").append(clazz.name());
        return sb.append(">").toString();
    }
}
