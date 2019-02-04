package rock.util;

public class BooleanRef {

    private boolean value;

    public BooleanRef(boolean value) {
        this.value = value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }
}
