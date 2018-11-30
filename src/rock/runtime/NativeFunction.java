package rock.runtime;

import rock.Environment;
import rock.Function;
import rock.RockException;
import rock.ast.ASTree;

import java.lang.reflect.Method;

public class NativeFunction extends Function {

    private Object obj;
    private Method method;

    public NativeFunction(String name, Object obj, Method method) {
        super(name, paramNames(method), null);
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
    public Object invoke(Environment env, Object... args) throws RockException {
        if (args.length != method.getParameterCount()) {
            throw new RockException("wrong number of args");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new RockException(e);
        }
    }
}
