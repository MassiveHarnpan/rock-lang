package rock.ast;

import rock.Enviroument;

public abstract class ASTree {


    public abstract boolean isLeaf();
    public abstract ASTree child(int i);
    public abstract int childCount();

    public abstract Object eval(Enviroument env);

    @Override
    public String toString() {
        return "("+getClass().getSimpleName()+")";
    }
}
