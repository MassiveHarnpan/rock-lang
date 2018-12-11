package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockClass;
import rock.exception.RockException;

public class ClassDef extends ASTList {
    public ClassDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }

    public String superClassName() {
        return childCount() == 3 ? ((Name) child(1)).token().literal() : null;
    }

    public ASTree body() {
        return child(childCount() - 1);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        RockClass superClass = null;
        String superClassName = superClassName();
        if (superClassName != null) {
            Object obj = env.get(superClassName());
            if (obj == null || !(obj instanceof RockClass)) {
                throw new RockException("cannot find class: " + superClassName);
            }
            superClass = (RockClass) obj;
        }
        RockClass newClass = new RockClass(name(), superClass, body(), env);
        return env.set(newClass.name(), newClass);
    }

    @Override
    public String toString() {
        String s = superClassName();
        if (s == null) {
            s = "";
        } else {
            s = "extends " + s;
        }
        return "class " + name() + " " + s + " " + body();
    }
}
