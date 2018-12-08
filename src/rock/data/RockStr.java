package rock.data;

import rock.exception.RockException;

public class RockStr extends RockLiteral {
    public RockStr(String value) {
        super(value);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        String left = (String) get();
        if ("+".equals(op)) {
            return new RockStr(((String) get()) + another.get());
        } else if ("*".equals(op) && another.get() instanceof Integer) {
            int right = (int) another.get();
            return new RockStr(repeat(right, left));
        }
        return super.compute(op, another);
    }




    public static String repeat(int times, String s) {
        if (times < 0) {
            s = new StringBuffer().append(s).reverse().toString();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
