package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.RockObject;
import rock.exception.UnsupportedASTException;
import rock.exception.UnsupportedOperationException;

public class Dot extends ASTList {
    public Dot(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }


    @Override
    public Object eval(Environment env, Object base) throws RockException {
        if (!(base instanceof RockObject)) {
            throw new UnsupportedOperationException("dot", base.getClass().getName());
        }
        if (!(child(0) instanceof Name)) {
            throw new UnsupportedASTException(child(0).getClass().getName(), Name.class.getName());
        }
        RockObject obj = (RockObject) base;
        return obj.get(name());
    }

    @Override
    public String toString() {
        return "." + name();
    }
}
