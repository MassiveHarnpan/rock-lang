package rock.ast;

import rock.Environment;
import rock.Function;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;

public class Arguments extends ASTList {

    public Arguments(ASTree... children) {
        super(children);
    }

    @Override
    public Object eval(Environment env, Object base) throws RockException {
        if (!(base instanceof Function)) {
            throw new UnsupportedOperationException("CallFunction", base.getClass().getName());
        }
        Function func = (Function) base;
        Object[] args = new Object[childCount()];
        for (int i = 0; i < childCount(); i++) {
            args[i] = child(i).eval(env);
        }
        return func.invoke(env, args);
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
