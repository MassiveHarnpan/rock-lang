package rock.data.internal;

public class RockNil extends RockAdapter {

    public static final RockNil INSTANCE = new RockNil();

    @Override
    public RockType type() {
        return RockType.NIL;
    }

    @Override
    public boolean hasJavaPrototype() {
        return true;
    }

    @Override
    public Object getJavaPrototype() {
        return null;
    }

    @Override
    public String toString() {
        return "nil";
    }
}
