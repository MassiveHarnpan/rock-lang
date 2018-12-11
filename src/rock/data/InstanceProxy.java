package rock.data;

import rock.exception.RockException;

public class InstanceProxy implements Proxy {

    private Rock instance;

    public InstanceProxy(Rock instance) {
        this.instance = instance;
    }

    @Override
    public Rock get() throws RockException {
        return instance;
    }

    @Override
    public Rock set(Rock val) throws RockException {
        throw new RockException("Cannot set value to the instance");
    }


}
