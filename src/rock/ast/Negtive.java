package rock.ast;

import rock.Environment;
import rock.RockException;

public class Negtive extends ASTList {

    public Negtive(ASTree... children) {
        super(children);
    }

    public ASTree origin() {
        return child(0);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        return -((Integer) origin().eval(env));
    }

    @Override
    public ASTree simplify() {
        return this;
    }
}
