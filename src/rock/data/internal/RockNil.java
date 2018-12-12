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
    public boolean support(String op, Rock another) {
        return "==".equals(op) || "!=".equals(op) || another.type() == RockType.STR;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        if ("==".equals(op)) {
            return another == this ? RockInteger.TRUE : RockInteger.FALSE;
        }
        if ("!=".equals(op)) {
            return another != this ? RockInteger.TRUE : RockInteger.FALSE;
        }
        if (another.type() == RockType.STR) {
            return new RockString(this.toString() + another.getJavaPrototype());
        }
        return super.compute(op, another);
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
