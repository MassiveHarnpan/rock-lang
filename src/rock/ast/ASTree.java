package rock.ast;

import rock.data.Evaluator;
import rock.data.internal.RockString;
import rock.token.Token;
import rock.util.IndentationPrinter;

public abstract class ASTree implements Evaluator {


    public abstract boolean isLeaf();
    public abstract Token token();
    public abstract ASTree child(int i);
    public abstract int childCount();

    public ASTree simplify() {
        return this;
    }

    @Override
    public String toString() {
        return "("+getClass().getSimpleName()+")";
    }

    public void write(IndentationPrinter printer) {
        printer.print(this.toString());
    }
}
