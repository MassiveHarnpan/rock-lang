package rock.ast;

import rock.Enviroument;

public class AssignStmt extends ASTList {



    public AssignStmt(ASTree... children) {
        super(children);

    }

    public String name() {
        return ((Name) child(0)).token().literal();
    }

    public ASTree value() {
        return child(1);
    }

    @Override
    public Object eval(Enviroument env) {
        String name = name();
        Object val = value().eval(env);
        System.out.println(name + " << " + val);
        env.put(name, val);
        return val;
    }
}
