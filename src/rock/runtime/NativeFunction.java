package rock.runtime;

import rock.data.internal.*;
import rock.data.Rock;
import rock.exception.RockException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class NativeFunction extends RockFunction {

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
            argarr[i] = args[i].hasJavaPrototype() ? args[i].getJavaPrototype() : args[i];
        }
        try {
            Object r = method.invoke(obj, argarr);
            if (r == null) {
                return RockNil.INSTANCE;
            }
            if (r instanceof Rock) {
                return (Rock) r;
            }
            if (r instanceof Byte || r instanceof Short ||r instanceof Integer || r instanceof Long || r instanceof BigInteger) {
                return new RockInteger(((Number) r).intValue());
            }
            if (r instanceof Float || r instanceof Double || r instanceof BigDecimal) {
                return new RockDecimal(((Number) r).doubleValue());
            }
            if (r instanceof Boolean) {
                boolean b = (boolean) r;
                return b ? RockInteger.TRUE : RockInteger.FALSE;
            }
            if (r instanceof String) {
                return new RockString((String) r);
            }
            if (r instanceof List) {
                List<Rock> list = (List<Rock>) r;
                return new RockArray(list.toArray(new Rock[list.size()]));
            }
            return new RockNative(r);
        } catch (Exception e) {
            throw new RockException(e);
        }
    }

    @Override
    public String toString() {
        return "<native-function:" + method.toString() + ">";
    }
}
