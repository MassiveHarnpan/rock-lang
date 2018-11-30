package rock.ast;

import rock.Environment;
import rock.Evaluator;
import rock.RockException;

public abstract class ASTree implements Evaluator {


    public abstract boolean isLeaf();
    public abstract ASTree child(int i);
    public abstract int childCount();

    public ASTree simplify() {
        return this;
    }

    @Override
    public String toString() {
        return "("+getClass().getSimpleName()+")";
    }
}
