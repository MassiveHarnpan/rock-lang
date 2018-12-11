package rock.data;

import rock.exception.RockException;

public interface Proxy {

    Rock get() throws RockException;
    Rock set(Rock val) throws RockException;

}
