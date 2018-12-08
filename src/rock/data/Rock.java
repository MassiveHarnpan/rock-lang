package rock.data;

import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class Rock implements GetterAndSetter {


    public Rock invoke(Rock... args) throws RockException {
        throw new UnsupportedOperationException("invoke", this.toString());
    }

    public Rock get(Rock key) throws RockException {
        throw new UnsupportedOperationException("get", this.toString());
    }

    @Override
    public Rock get(String key) throws RockException {
        throw new UnsupportedOperationException("get", this.toString(), key);
    }

    public Object get() throws RockException {
        throw new UnsupportedOperationException("get", this.toString());
    }

    @Override
    public Rock set(String key, Rock val) throws RockException {
        throw new UnsupportedOperationException("set", this.toString(), key, val.toString());
    }

    public Rock set(Rock val) throws RockException {
        throw new UnsupportedOperationException("set", this.toString());
    }


    public Rock compute(String op, Rock another) throws RockException {
        throw new UnsupportedOperationException("operate", this.toString());
    }

    public Environment env() throws RockException {
        throw new UnsupportedOperationException("env", this.toString());
    }


}
