package rock.data;

import rock.exception.RockException;

public class EnvProxy implements Proxy {


    private Environment env;
    private Object key;

    public EnvProxy(Environment env, Object key) {
        this.env = env;
        this.key = key;
    }

    @Override
    public Rock get() throws RockException {
        return env.get(key);
    }

    @Override
    public Rock set(Rock val) throws RockException {
        return env.set(key, val);
    }


    public Object key() {
        return key;
    }

    public Environment env() {
        return env;
    }

}
