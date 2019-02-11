package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockClass;
import rock.data.internal.RockString;
import rock.data.internal.RockType;
import rock.exception.RockException;

public class ClassDef extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new ClassDef(elements);

    public ClassDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return child(0).token().literal();
    }

    public String superClassName() {
        return childCount() == 3 ? child(1).token().literal() : null;
    }

    public ASTree body() {
        return child(childCount() - 1);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {

        String superClassName = superClassName();
        String className = name();
        if (superClassName == null) {
            superClassName = "Object";
        }
        Rock superClass = env.get(superClassName);
        if (superClass == null || superClass.type() != RockType.CLS) {
            throw new RockException("cannot find super class of " + className + ": " + superClassName);
        }
        RockClass newClass = new RockClass(className, (RockClass) superClass, body(), env);
        return env.set(newClass.name(), newClass);
    }
}
