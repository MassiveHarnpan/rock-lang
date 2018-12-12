package rock.ast;

import rock.data.Evaluator;
import rock.data.internal.RockString;

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

    public String toString(int indent, String space) {
        return RockString.repeat(indent, space) + this.toString();
    }
}
