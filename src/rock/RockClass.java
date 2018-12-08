package rock;

import rock.exception.RockException;
import rock.runtime.NativeEvaluator;

public class RockClass extends RockObject {

    private String name;
    private RockClass superClass;
    private Evaluator body;

    public RockClass(String name, RockClass superClass, Evaluator body, Environment outer) {
        super(null, outer);
        this.superClass = superClass;
        this.name = name;
        this.body = body;
    }

    public String name() {
        return name;
    }

    public Evaluator body() {
        return body;
    }

    public Function getConstructor(String constructorName) throws RockException {
        RockObject ro = new RockObject(RockClass.this, outer());
        initObject(ro);
        Object con = ro.get(constructorName);
        if (con == null || !(con instanceof Function)) {
            throw new RockException("Cannot find constructor: " + constructorName);
        }
        Function constructor = (Function) con;
        return new Function(constructorName, null, null, outer()) {
            @Override
            public Object invoke(Environment env, Object... args) throws RockException {
                constructor.invoke(ro, args);
                return ro;
            }
        };
    }
    public void initObject(RockObject ro) throws RockException {
        if (superClass != null) {
            superClass.initObject(ro);
        }
        body.eval(ro);
    }

    @Override
    public Object get(String key) throws RockException {
        return getConstructor(key);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<class:").append(name);
        if (superClass != null) {
            sb.append(" extends ").append(superClass.name);
        }
        return  sb.append(">").toString();
    }
}
