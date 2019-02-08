package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;

public class RockNil extends RockAdapter {

    public static final RockNil INSTANCE = new RockNil();

    @Override
    public RockType type() {
        return RockType.NIL;
    }

    @Override
    public boolean asBoolean() throws RockException {
        return false;
    }

    @Override
    public String asString() throws RockException {
        return "nil";
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return null;
    }

    @Override
    public String toString() {
        return "nil";
    }
}
