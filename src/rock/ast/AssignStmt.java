package rock.ast;

import rock.Environment;
import rock.RockObject;
import rock.exception.RockException;
import rock.exception.UnsupportedASTException;
import rock.exception.UnsupportedOperationException;
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
    public Object eval(Environment env) throws RockException {
        ASTree dest = name();
        Object val = value().eval(env);
        Logger.log(dest + " << " + val);

        if (dest instanceof Name) {
            Name name = (Name) dest;
            env.put(name.token().literal(), val);
        } else if (dest instanceof Primary) {
            Primary primary = (Primary) dest;
            if (!(primary.postfixCount() <= 0 || primary.postfix(0) instanceof Dot)) {
                throw new UnsupportedOperationException("assign", primary.postfix(0).getClass().getName());
            }
            Object obj = primary.evalSub(env, 1);
            if (obj instanceof RockObject) {
                RockObject ro = (RockObject) obj;
                ro.put(((Dot) primary.child(primary.childCount() - 1)).name(), val);
            } else {
                throw new UnsupportedOperationException("assign", obj.getClass().getName());
            }
        } else {
            throw new UnsupportedASTException(dest.getClass().getName(), Name.class.getName(), Primary.class.getName());
        }
        return val;
    }

    @Override
    public String toString() {
        return name().toString() + " = " + value().toString();
    }
}
