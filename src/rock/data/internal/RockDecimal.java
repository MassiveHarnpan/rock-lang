package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockDecimal extends RockLiteral {
    public RockDecimal(double value) {
        super(value);
    }

    @Override
    public RockType type() {
        return RockType.DEC;
    }

    @Override
    public boolean support(String op, Rock another) {
        if (another.type() == RockType.INT || another.type() == RockType.DEC) {
            return true;
        }
        if (another.type() == RockType.STR && "+".equals(op)) {
            return true;
        }
        return false;
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        double left = (double) getJavaPrototype();
        Object o;
        if (!hasJavaPrototype() || (o = another.getJavaPrototype()) == null) {
            return null;
        }
        double rst = 0;
        if (!(o instanceof Number)) {
            throw new UnsupportedOperationException("op", String.valueOf(o));
        }
        double right = ((Number) o).doubleValue();
        switch (op) {
            case "+": rst = left + right; break;
            case "-": rst = left - right; break;
            case "*": rst = left * right; break;
            case "/": rst = left / right; break;
            case "%": rst = left % right; break;
            case ">": return new RockInteger(left > right ? 1 : 0);
            case ">=": return new RockInteger(left >= right ? 1 : 0);
            case "<": return new RockInteger(left < right ? 1 : 0);
            case "<=": return new RockInteger(left <= right ? 1 : 0);
            case "!=": return new RockInteger(left != right ? 1 : 0);
            case "==": return new RockInteger(left == right ? 1 : 0);
        }
        return new RockDecimal(rst);
    }
}
