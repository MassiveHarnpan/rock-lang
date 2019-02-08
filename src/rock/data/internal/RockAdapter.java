package rock.data.internal;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockAdapter extends Rock {


    @Override
    public Rock getMember(String mem) throws RockException {
        throw new RockException("cannot get setMember of " + toString());
    }

    @Override
    public Rock setMember(String mem, Rock value) throws RockException {
        throw new RockException("cannot set setMember of " + toString());
    }

    @Override
    public boolean support(String op, Rock another) {
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        return null;
    }

    @Override
    public Environment env() throws RockException {
        return null;
    }

    @Override
    public RockType type() {
        return null;
    }

    @Override
    public int asInt() throws RockException {
        throw new RockException("cannot cast " + toString() + " to int");
    }

    @Override
    public double asDecimal() throws RockException {
        throw new RockException("cannot cast " + toString() + " to decimal");
    }

    @Override
    public boolean asBoolean() throws RockException {
        throw new RockException("cannot cast " + toString() + " to boolean");
    }

    @Override
    public String asString() throws RockException {
        return toString();
    }

    @Override
    public boolean hasJavaPrototype() {
        return false;
    }

    @Override
    public Object getJavaPrototype() {
        return null;
    }

    @Override
    public Rock get(Object key) throws RockException {
        throw new UnsupportedOperationException("get", this.toString(), key.toString());
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        throw new UnsupportedOperationException("set", this.toString(), key.toString(), val.toString());
    }
}
