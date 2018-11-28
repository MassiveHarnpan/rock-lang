package rock.ast;

import rock.Environment;
import rock.RockException;
import rock.util.Logger;

public class AssignStmt extends ASTList {



    public AssignStmt(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((Name) child(0)).token().literal();
    }

    public ASTree value() {
        return child(1);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        String name = name();
        Object val = value().eval(env);
        //Logger.log(name + " << " + val);
        env.put(name, val);
        return val;
    }
}
