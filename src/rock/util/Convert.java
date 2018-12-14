package rock.util;

import rock.data.Rock;
import rock.data.internal.RockInteger;

public class Convert {

    public static boolean toBoolean(Rock rock) {
        if (rock == null) {
            return false;
        }
        if (RockInteger.FALSE.equals(rock)) {
            return false;
        }
        return true;
    }
}
