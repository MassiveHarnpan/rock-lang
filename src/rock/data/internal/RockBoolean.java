package rock.data.internal;

import rock.exception.RockException;

public class RockBoolean extends RockAdapter {

    public static final RockBoolean TRUE = new RockBoolean(true);
    public static final RockBoolean FALSE = new RockBoolean(false);

    public static RockBoolean valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    private boolean value;

    public RockBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public RockType type() {
        return RockType.BOO;
    }

    @Override
    public boolean asBoolean() throws RockException {
        return value;
    }

    @Override
    public int asInt() throws RockException {
        return value ? 1 : 0;
    }

    @Override
    public double asDecimal() throws RockException {
        return asInt();
    }

    @Override
    public String asString() throws RockException {
        return value ? "true" : "false";
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
