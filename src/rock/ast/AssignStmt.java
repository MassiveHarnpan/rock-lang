package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedASTException;
import rock.util.Logger;

public class AssignStmt extends ASTList {



    public AssignStmt(ASTree... children) {
        super(children);
    }

    public ASTree name() {
        return child(0);
    }

    public ASTree value() {
        return child(1);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        ASTree dest = name();
        Rock val = value().eval(env);
        Logger.log(dest + " << " + val);


        if (dest instanceof Name || dest instanceof Primary) {
            Proxy name = dest.proxy(env, null);
            name.set(val);
            return val;
        } else {
            throw new UnsupportedASTException(dest.getClass().getName(), Name.class.getName(), Primary.class.getName());
        }
    }

    @Override
    public String toString() {
        return name().toString() + " = " + value().toString();
    }
}
