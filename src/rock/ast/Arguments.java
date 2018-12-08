package rock.ast;

import rock.data.Rock;
import rock.data.Environment;
import rock.data.Function;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class Arguments extends ASTList {

    public Arguments(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock[] args = new Rock[childCount()];
        for (int i = 0; i < childCount(); i++) {
            args[i] = child(i).eval(env);
        }
        return base.invoke(args);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        if (childCount() > 0) {
            sb.append(child(0).toString());
        }
        for (int i = 1; i < childCount(); i++) {
            sb.append(", ").append(child(i).toString());
        }
        return sb.append(")").toString();
    }
}
