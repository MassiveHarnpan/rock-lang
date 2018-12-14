package rock.data.internal;

import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.runtime.BasicRuntimeEnvironment;
import rock.util.Logger;

public class RockClass extends RockAdapter {

    private String name;
    private RockClass superClass;
    private Evaluator body;
    private Environment outer;

    public RockClass(String name, RockClass superClass, Evaluator body, Environment outer) {
        super();
        this.superClass = superClass;
        this.name = name;
        this.body = body;
        this.outer = outer;
    }

    @Override
    public RockType type() {
        return RockType.CLS;
    }

    public String name() {
        return name;
    }

    public Evaluator body() {
        return body;
    }

    public Environment outer() {
        return outer;
    }

    public RockClass superClass() {
        return superClass;
    }





    public void initObject(RockObject ro) throws RockException {
        if (superClass != null) {
            superClass.initObject(ro);
        }
        body.eval(ro.env());
    }


    @Override
    public Rock invoke(Rock... args) throws RockException {
        RockObject ro = new RockObject(this, outer());
        this.initObject(ro);
        Rock function = ro.env().get(RockObject.NEW);
        if (function != null) {
            function.invoke(args);
        } else {
            if (args.length != 0) {
                throw new RockException("wrong arg count: expect 0, get " + args.length);
            }
        }
        return ro;
    }

    @Override
    public Rock member(String key) throws RockException {
        if (!(key instanceof String)) {
            throw new RockException("the name of a constructor must be a string");
        }
        Logger.log("create new constructor: " + name + "." + key);
        return new RockConstructor(this, (String) key);
    }

    @Override
    public Environment env() throws RockException {
        return outer;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<class:").append(name);
        if (superClass != null && !"Object".equals(superClass.name)) {
            sb.append(" extends ").append(superClass.name);
        }
        return  sb.append(">").toString();
    }
}
