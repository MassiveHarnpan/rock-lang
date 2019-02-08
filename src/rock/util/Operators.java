package rock.util;

import rock.data.Rock;
import rock.data.internal.RockBoolean;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
import rock.exception.RockComputeException;
import rock.exception.RockException;

import static rock.data.internal.RockType.*;

import java.util.ArrayList;
import java.util.List;

public class Operators {

    public static Rock compute(Rock former, String op, Rock later) throws RockException {
        if ("!".equals(op)) {
            return RockBoolean.valueOf(later.asBoolean());
        }
        switch (op) {
            case "&&": return RockBoolean.valueOf(former.asBoolean() && later.asBoolean());
            case "||": return RockBoolean.valueOf(former.asBoolean() || later.asBoolean());
        }
        if (former.type() == STR || later.type() == STR) {
            if ("+".equals(op)) {
                return new RockString(former.asString() + later.asString());
            } else if ("*".equals(op)) {
                String s = "";
                int times = 0;
                if (former.type() == INT) {
                    times = former.asInt();
                    s = later.asString();
                } else if (later.type() == INT) {
                    times = later.asInt();
                    s = former.asString();
                } else {
                    throw new RockComputeException(former, op, later);
                }
                return new RockString(RockString.repeat(times, s));
            } else {
                throw new RockComputeException(former, op, later);
            }
        }
        if (former.type() == INT && later.type() == INT) {
            int f = former.asInt();
            int l = later.asInt();
            switch (op) {
                case "+": return new RockInteger(f + l);
                case "-": return new RockInteger(f - l);
                case "*": return new RockInteger(f * l);
                case "/": return new RockInteger(f / l);
                case "^": return new RockDecimal(Math.pow(f, l));
                case "==": return RockBoolean.valueOf(f == l);
                case "!=": return RockBoolean.valueOf(f != l);
                case ">": return RockBoolean.valueOf(f > l);
                case "<": return RockBoolean.valueOf(f < l);
                case ">=": return RockBoolean.valueOf(f >= l);
                case "<=": return RockBoolean.valueOf(f <= l);
            }
        }
        if (former.type() == DEC || later.type() == DEC) {
            double f = former.asDecimal();
            double l = later.asDecimal();
            switch (op) {
                case "+": return new RockDecimal(f + l);
                case "-": return new RockDecimal(f - l);
                case "*": return new RockDecimal(f * l);
                case "/": return new RockDecimal(f / l);
                case "^": return new RockDecimal(Math.pow(f, l));
                case "==": return RockBoolean.valueOf(f == l);
                case "!=": return RockBoolean.valueOf(f != l);
                case ">": return RockBoolean.valueOf(f > l);
                case "<": return RockBoolean.valueOf(f < l);
                case ">=": return RockBoolean.valueOf(f >= l);
                case "<=": return RockBoolean.valueOf(f <= l);
            }
        }
        throw new RockComputeException(former, op, later);
    }




}
