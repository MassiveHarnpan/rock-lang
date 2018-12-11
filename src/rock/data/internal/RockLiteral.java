package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;

public class RockLiteral extends RockAdapter {

    private Object value;

    public RockLiteral(Object value) {
        this.value = value;
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
