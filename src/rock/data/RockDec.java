package rock.data;

import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockDec extends RockLiteral {
    public RockDec(double value) {
        super(value);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        double left = (double) get();
        Object o = another.get();
        double rst = 0;
        if (!(o instanceof Number)) {
            throw new UnsupportedOperationException("op", String.valueOf(another.get()));
        }
        double right = ((Number) o).doubleValue();
        switch (op) {
            case "+": rst = left + right; break;
            case "-": rst = left - right; break;
            case "*": rst = left * right; break;
            case "/": rst = left / right; break;
            case "%": rst = left % right; break;
            case ">": return new RockInt(left > right ? 1 : 0);
            case ">=": return new RockInt(left >= right ? 1 : 0);
            case "<": return new RockInt(left < right ? 1 : 0);
            case "<=": return new RockInt(left <= right ? 1 : 0);
            case "!=": return new RockInt(left != right ? 1 : 0);
            case "==": return new RockInt(left == right ? 1 : 0);
        }
        return new RockDec(rst);
    }
}
