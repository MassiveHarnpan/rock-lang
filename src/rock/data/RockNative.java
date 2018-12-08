package rock.data;

import rock.exception.RockException;

import java.lang.reflect.Method;

public class RockNative extends Rock {

    private Object base;
    private Object value;

    public RockNative(Object base, Object value) {
        this.base = base;
        this.value = value;
    }

    public RockNative(Object value) {
        this.value = value;
    }
}
