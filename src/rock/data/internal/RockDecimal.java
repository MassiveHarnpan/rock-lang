package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockDecimal extends RockAdapter {

    private double value;

    public RockDecimal(double value) {
        this.value = value;
    }

    @Override
    public RockType type() {
        return RockType.DEC;
    }

    @Override
    public int asInt() throws RockException {
        return (int) value;
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
    public String toString() {
        return String.valueOf(value);
    }
}
