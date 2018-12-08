package rock.runtime;

import rock.data.Function;
import rock.data.Rock;
import rock.exception.RockException;

import java.lang.reflect.Method;

public class NativeFunction extends Function {

    private Object obj;
    private Method method;

    public NativeFunction(String name, Object obj, Method method) {
        super(name, paramNames(method), null, null);
        this.obj = obj;
        this.method = method;
    }

    public static String[] paramNames(Method method) {
        String[] params = new String[method.getParameterCount()];
        for (int i = 0; i < params.length; i++) {
            params[i] = "arg" + i;
        }
        return params;
    }


    public NativeFunction(String name, Method method) {
        this(name, null, method);
    }

    @Override
    public Rock invoke(Rock... args) throws RockException {
        if (args.length != method.getParameterCount()) {
            throw new RockException("wrong number of args");
        }
        Object[] argarr = new Object[args.length];
        for (int i = 0; i < argarr.length; i++) {
            argarr[i] = args[i].get();
        }
        try {
            Object r = method.invoke(obj, argarr);
            return r == null ? null : new Rock();
        } catch (Exception e) {
            throw new RockException(e);
        }
    }

    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + ":" + method.toString() + ">";
    }
}
