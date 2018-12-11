package rock.data.internal;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockClass;
import rock.data.internal.RockObject;
import rock.exception.RockException;

import java.util.Set;

public class RockConstructor extends RockAdapter {

    private RockClass clazz;
    private String name;

    public RockConstructor(RockClass clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    @Override
    public Rock invoke(Rock... args) throws RockException {
        RockObject ro = new RockObject(clazz, clazz.outer());
        clazz.initObject(ro);
        Rock function = ro.env().get(name);
        if (function == null) {
            throw new RockException("Cannot find constructor: " + name + " of " + clazz.name());
        }
        function.invoke(args);
        return ro;
    }

    @Override
    public Environment env() throws RockException {
        return clazz.env();
    }

    @Override
    public RockType type() {
        return RockType.CON;
    }

    @Override
    public Rock get(Object key) throws RockException {
        return null;
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        return null;
    }

    @Override
    public String toString() {
        return "<constructor:" + name + " of " + clazz.name() + ">";
    }
}
