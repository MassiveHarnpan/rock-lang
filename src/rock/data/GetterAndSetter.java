package rock.data;

import rock.exception.RockException;

public interface GetterAndSetter {
    Rock get(String key) throws RockException;
    Rock set(String key, Rock val) throws RockException;
}
