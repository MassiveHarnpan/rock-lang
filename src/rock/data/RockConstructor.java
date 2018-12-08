package rock.data;

import rock.exception.RockException;

public class RockConstructor extends Rock {

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
    public String toString() {
        return "<constructor:" + name + " of " + clazz.name() + ">";
    }
}
