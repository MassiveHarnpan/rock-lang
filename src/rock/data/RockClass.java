package rock.data;

import rock.exception.RockException;
import rock.util.Logger;

public class RockClass extends Rock {

    private String name;
    private RockClass superClass;
    private Evaluator body;
    private Environment outer;

    public RockClass(String name, RockClass superClass, Evaluator body, Environment outer) {
        this.superClass = superClass;
        this.name = name;
        this.body = body;
        this.outer = outer;
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
    public Rock get(String key) throws RockException {
        Logger.log("create new constructor: " + name + "." + key);
        return new RockConstructor(this, key);
    }

    @Override
    public Environment env() throws RockException {
        return outer;
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
