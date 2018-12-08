package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.RockName;
import rock.exception.RockException;
import rock.data.RockObject;
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
    public Rock eval(Environment env, Rock base) throws RockException {
        if (!(child(0) instanceof Name)) {
            throw new UnsupportedASTException(child(0).getClass().getName(), Name.class.getName());
        }
        return child(0).eval(base.env(), base);
    }

    @Override
    public String toString() {
        return "." + name();
    }
}
