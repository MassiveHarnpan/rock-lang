package rock.ast;

import rock.data.Environment;
import rock.data.Proxy;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.Iterator;

public class Settable extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Settable(elements);

    public Settable(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        // TODO
        return super.eval(env);
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        // TODO
        return super.proxy(env, base);
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
}
