package rock.data;

import rock.exception.RockException;
import rock.util.Logger;

public class RockName extends Rock {

    private GetterAndSetter gas;
    private String name;

    public RockName(GetterAndSetter gas, String name) {
        this.gas = gas;
        this.name = name;
    }

    public GetterAndSetter gas() {
        return gas;
    }

    @Override
    public Rock set(Rock val) throws RockException {
        Logger.log("# set " + val + " to " + name + " of " + gas.getClass());
        if (gas instanceof RockName) {
            Logger.log("gas.origin = " + ((RockName) gas).gas);
        }
        return gas.set(name, val);
    }

    @Override
    public Rock set(String key, Rock val) throws RockException {
        Rock rock = gas.get(name);
        if (rock == null) {
            return super.set(key, val);
        }
        return rock.set(key, val);
    }

    @Override
    public Object get() throws RockException {
        Rock rock = gas.get(name);
        Logger.log("Looking for " + name + " in " + gas + ", result = " + rock);
        return rock == null ? null : rock.get();
    }




    @Override
    public Rock invoke(Rock... args) throws RockException {
        Rock rock = gas.get(name);
        Logger.log("Looking for " + name + " in " + gas + ", result = " + rock);
        if (rock == null) {
            return super.invoke(args);
        }
        return rock.invoke(args);
    }

    @Override
    public Rock get(Rock key) throws RockException {
        Rock rock = gas.get(name);
        if (rock == null) {
            return super.get(key);
        }
        return rock.get(key);
    }

    @Override
    public Rock get(String key) throws RockException {
        Rock rock = gas.get(name);
        if (rock == null) {
            return super.get(key);
        }
        return rock.get(key);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        Rock rock = gas.get(name);
        if (rock == null) {
            return super.compute(op, another);
        }
        return rock.compute(op, another);
    }


    @Override
    public Environment env() throws RockException {
        Rock rock = gas.get(name);
        if (rock == null) {
            return super.env();
        }
        return rock.env();
    }

    @Override
    public String toString() {
        try {
            return "<proxy:" + gas.get(name) + ">";
        } catch (RockException e) {
            e.printStackTrace();
            return "<proxy>";
        }
    }
}
