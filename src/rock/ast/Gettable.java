package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.Iterator;

public class Gettable extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Gettable(elements);

    public Gettable(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        // TODO
        return super.eval(env);
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
