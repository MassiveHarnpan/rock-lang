package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

import java.util.Iterator;

public class Gettable extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Gettable(elements);

    public Gettable(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock rock = null;
        for (int i = 0; i < childCount(); i++) {
            rock = child(i).eval(env, rock);
        }
        return rock;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            builder.append(itr.next());
        }
        return builder.toString();
    }

    @Override
    public void write(IndentationPrinter printer) {
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            itr.next().write(printer);
        }
    }
}
