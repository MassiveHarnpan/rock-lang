package rock.ast;

import rock.Enviroument;

import java.util.Iterator;

public class Block extends ASTList {

    public Block(ASTree... children) {
        super(children);
    }

    @Override
    public Object eval(Enviroument env) {
        Object result = null;
        for (Iterator<ASTree> it = iterator(); it.hasNext(); ) {
            result = it.next().eval(env);
        }
        return result;
    }
}
