package rock.ast;

import rock.data.EnvProxy;
import rock.data.Environment;
import rock.data.Proxy;
import rock.data.Rock;
import rock.exception.RockException;

public class Index extends ASTList{

    public static final ASTListFactory FACTORY = elements -> new Index(elements);

    public Index(ASTree... children) {
        super(children);
    }


    public ASTree index() {
        return child(0);
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        Object key = index().eval(env).getJavaPrototype();
        return new EnvProxy(base, key);
    }

    @Override
    public String toString() {
        return "[" + index() + "]";
    }
}
