package rock.ast;

public abstract class ASTree {


    public abstract boolean isLeaf();
    public abstract ASTree child(int i);
    public abstract int childCount();

    @Override
    public String toString() {
        return "("+getClass().getSimpleName()+")";
    }
}
