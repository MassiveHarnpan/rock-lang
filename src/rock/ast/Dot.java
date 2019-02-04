package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedASTException;

public class Dot extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Dot(elements);

    public Dot(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }


    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        return new MemberProxy(base, name());
    }

    @Override
    public String toString() {
        return "." + name();
    }
}
