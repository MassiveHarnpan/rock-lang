package rock.data;

import rock.exception.RockException;

public class RockLiteral extends Rock {

    private Object value;

    public RockLiteral(Object value) {
        this.value = value;
    }

    @Override
    public Object get() throws RockException {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
