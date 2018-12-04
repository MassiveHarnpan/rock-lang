package rock.ast;

import rock.Environment;
import rock.RockClass;
import rock.RockException;

public class ClassDef extends ASTList {
    public ClassDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }

    public ASTree body() {
        return child(1);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        RockClass newClass = new RockClass(name(), body());
        return env.put(newClass.name(), newClass);
    }
}
