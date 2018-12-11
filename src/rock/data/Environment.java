package rock.data;

import rock.exception.RockException;

public interface Environment {
    Rock get(Object key) throws RockException;
    Rock set(Object key, Rock val) throws RockException;
}
