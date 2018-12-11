package rock.data.internal;

import rock.data.Rock;
import rock.exception.RockException;

import java.lang.reflect.Method;

public class RockNative extends RockAdapter {

    private Object object;

    public RockNative(Object object) {
        this.object = object;
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return object;
    }


    @Override
    public String toString() {
        return "<native-object:" + object + ">";
    }
}
