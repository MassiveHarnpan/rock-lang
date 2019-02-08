package rock.data;

import rock.data.internal.RockType;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public abstract class Rock implements Environment {


    public Rock invoke(Rock... args) throws RockException {
        throw new UnsupportedOperationException("invoke", this.toString());
    }

    public Rock get(Rock key) throws RockException {
        if (key == null) {
            return null;
        }
        if (key.hasJavaPrototype()) {
            return get(key.getJavaPrototype());
        }
        return get((Object) key);
    }

    public abstract Rock getMember(String mem) throws RockException;

    public abstract Rock setMember(String mem, Rock value) throws RockException;

    public abstract boolean support(String op, Rock another);

    public abstract Rock compute(String op, Rock another) throws RockException;

    public abstract Environment env() throws RockException;

    public abstract RockType type();

    public abstract int asInt() throws RockException;

    public abstract double asDecimal() throws RockException;

    public abstract boolean asBoolean() throws RockException;

    public abstract String asString() throws RockException;



    public abstract boolean hasJavaPrototype();

    public Object getJavaPrototype() {
        return this;
    }

}
