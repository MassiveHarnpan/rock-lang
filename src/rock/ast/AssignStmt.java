package rock.ast;

import rock.Enviroument;

public class AssignStmt extends ASTList {



    public AssignStmt(ASTree... children) {
        super(children);

    }

    public Name name() {
        return ((Name) child(0));
    }

    public ASTree value() {
        return child(1);
    }

    @Override
    public Object eval(Enviroument env) {
        String name = name().token().literal();
        Object val = value().eval(env);
        env.put(name, val);
        return val;
    }
}
