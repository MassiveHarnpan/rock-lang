package rock.ast;

import rock.data.*;
import rock.exception.RockException;

import java.util.Iterator;

public class Arguments extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Arguments(elements);

    public Arguments(ASTree... children) {
        super(children);
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        Rock[] args = new Rock[childCount()];
        for (int i = 0; i < childCount(); i++) {
            args[i] = child(i).eval(env);
        }
        return new InstanceProxy(base.invoke(args));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }
}
