package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;

public class RockString extends RockLiteral {
    public RockString(String value) {
        super(value);
    }

    @Override
    public RockType type() {
        return RockType.STR;
    }

    @Override
    public boolean support(String op, Rock another) {
        if ("+".equals(op)) {
            return true;
        }
        if ("*".equals(op) && another.type() == RockType.INT) {
            return true;
        }
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        String left = (String) getJavaPrototype();
        Object right = another.hasJavaPrototype() ? another.getJavaPrototype() : another;
        if ("+".equals(op)) {
            return new RockString(left + right);
        } else if ("*".equals(op) && right instanceof Integer) {
            int times = (int) right;
            return new RockString(repeat(times, left));
        }
        return super.compute(op, another);
    }




    public static String repeat(int times, String s) {
        if (times < 0) {
            s = new StringBuffer().append(s).reverse().toString();
            times = -times;
        }
        switch (times) {
            case 0: return "";
            case 1: return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
