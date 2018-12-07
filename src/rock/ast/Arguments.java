package rock.ast;

import rock.Environment;
import rock.Function;
import rock.RockException;
import rock.util.Logger;

import java.util.Arrays;
import java.util.List;

public class Arguments extends ASTList {

    public Arguments(ASTree... children) {
        super(children);
    }

    @Override
    public Object eval(Environment env, Object base) throws RockException {
        Function func = (Function) base;
        Object[] args = new Object[childCount()];
        for (int i = 0; i < childCount(); i++) {
            args[i] = child(i).eval(env);
        }
        return func.invoke(env, args);
    }
}
