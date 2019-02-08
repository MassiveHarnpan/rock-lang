package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.Logger;

import java.util.Arrays;

public class Simple extends ASTList {

    public Simple(ASTree... children) {
        super(children);
    }

    public ASTree expr() {
        return child(0);
    }

    public ASTree args() {
        return child(1);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock func = expr().eval(env, base);
        ASTree args = args();
        Rock[] argarr = new Rock[args.childCount()];
        for (int i = 0; i < argarr.length; i++) {
            argarr[i] = args.child(i).eval(env, base);
        }
        Logger.log("add args: " + Arrays.asList(argarr));
        return func.invoke(argarr);
    }

}
