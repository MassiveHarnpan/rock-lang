package rock.ast;

import rock.Environment;
import rock.RockException;

public abstract class ASTree {


    public abstract boolean isLeaf();
    public abstract ASTree child(int i);
    public abstract int childCount();

    public abstract Object eval(Environment env) throws RockException;

    public ASTree simplify() {
        return this;
    }

    @Override
    public String toString() {
        return "("+getClass().getSimpleName()+")";
    }
}
