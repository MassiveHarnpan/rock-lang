package rock.ast;

import rock.Environment;
import rock.RockException;
import rock.RockObject;

public class Dot extends ASTList {
    public Dot(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }


    @Override
    public Object eval(Environment env, Object base) throws RockException {
        RockObject obj = (RockObject) base;
        return obj.get(name());
    }
}
