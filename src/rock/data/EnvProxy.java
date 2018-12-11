package rock.data;

import rock.exception.RockException;

public class EnvProxy implements Proxy {


    private Environment env;
    private String name;

    public EnvProxy(Environment env, String name) {
        this.env = env;
        this.name = name;
    }

    @Override
    public Rock get() throws RockException {
        return env.get(name);
    }

    @Override
    public Rock set(Rock val) throws RockException {
        return env.set(name, val);
    }


    public String name() {
        return name;
    }

    public Environment env() {
        return env;
    }

}
