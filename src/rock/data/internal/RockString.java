package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;

public class RockString extends RockAdapter {

    private String value;

    public RockString(String value) {
        this.value = value;
    }

    @Override
    public RockType type() {
        return RockType.STR;
    }

    @Override
    public boolean asBoolean() throws RockException {
        return !value.isEmpty();
    }

    @Override
    public String asString() throws RockException {
        return value;
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

    @Override
    public String toString() {
        return '\"' + value.replaceAll("\"", "\\\"").replaceAll("\n", "\\\n") + '\"';
    }
}
