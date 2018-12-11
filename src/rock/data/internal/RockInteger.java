package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockInteger extends RockLiteral {

    public static final RockInteger TRUE = new RockInteger(1);
    public static final RockInteger FALSE = new RockInteger(0);

    public RockInteger(Integer value) {
        super(value);
    }

    @Override
    public RockType type() {
        return RockType.INT;
    }

    @Override
    public boolean support(String op, Rock another) {
        if (another.type() == RockType.INT || another.type() == RockType.DEC) {
            return true;
        }
        if (another.type() == RockType.STR && ("+".equals(op) || "*".equals(op))) {
            return true;
        }
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        int left = (int) getJavaPrototype();
        Object o = another.getJavaPrototype();
        double rst = 0;
        if (o instanceof Number) {
            double right = ((Number) o).doubleValue();
            switch (op) {
                case "+": rst = left + right;break;
                case "-": rst = left - right;break;
                case "*": rst = left * right;break;
                case "/": rst = left / right;break;
                case "%": rst = left % right;break;
                case ">": rst = left > right ? 1 : 0;break;
                case ">=": rst = left >= right ? 1 : 0;break;
                case "<": rst = left < right ? 1 : 0;break;
                case "<=": rst = left <= right ? 1 : 0; break;
                case "!=": rst = left != right ? 1 : 0; break;
                case "==": rst = left == right ? 1 : 0; break;
            }
            return new RockInteger((int) rst);
        } else if (o instanceof String) {
            String right = (String) o;
            switch (op) {
                case "+": return new RockString(left + right);
                case "*": return new RockString(RockString.repeat(left, right));
            }
        }
        throw new UnsupportedOperationException("op", String.valueOf(o));
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
}
