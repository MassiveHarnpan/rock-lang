package rock.data;

import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class RockInt extends RockLiteral {

    public RockInt(Integer value) {
        super(value);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        int left = (int) get();
        Object o = another.get();
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
            return new RockInt((int) rst);
        } else if (o instanceof String) {
            String right = (String) o;
            switch (op) {
                case "+": return new RockStr(left + right);
                case "*": return new RockStr(RockStr.repeat(left, right));
            }
        }
        throw new UnsupportedOperationException("op", String.valueOf(another.get()));
    }
}
