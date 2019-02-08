package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockInteger extends RockAdapter {

    public static final RockInteger TRUE = new RockInteger(1);
    public static final RockInteger FALSE = new RockInteger(0);

    private int value;

    public RockInteger(int value) {
        this.value = value;
    }

    @Override
    public RockType type() {
        return RockType.INT;
    }


    @Override
    public int asInt() throws RockException {
        return value;
    }

    @Override
    public double asDecimal() throws RockException {
        return value;
    }

    @Override
    public boolean asBoolean() throws RockException {
        return value != 0;
    }

    @Override
    public String asString() throws RockException {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rock)) {
            return false;
        }
        Rock rock = (Rock) obj;
        Integer val = (Integer) getJavaPrototype();
        if (rock.type() == RockType.INT || rock.type() == RockType.DEC) {
            return val.equals(rock.getJavaPrototype());
        }
        if (rock.type() == RockType.NIL) {
            return val == 0;
        }
        if (rock.type() == RockType.STR) {
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
