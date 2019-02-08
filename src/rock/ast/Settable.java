package rock.ast;

import rock.data.Environment;
import rock.data.Proxy;
import rock.data.Rock;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

import java.util.Iterator;

public class Settable extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Settable(elements);

    public Settable(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock rock = base;
        for (int i = 0; i < childCount(); i++) {
            rock = child(i).eval(env, rock);
        }
        return rock;
    }

    @Override
    public Rock set(Environment env, Rock base, Rock value) throws RockException {
        Rock rock = base;
        for (int i = 0; i < childCount() - 1; i++) {
            rock = child(i).eval(env, rock);
        }
        String name = child(childCount() - 1).token().literal();
        return base == null ? env.set(name, value) : base.set(name, value);
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
