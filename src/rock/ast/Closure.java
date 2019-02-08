package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockFunction;

import java.util.ArrayList;
import java.util.List;

public class Closure extends ASTList {
    public Closure(ASTree... children) {
        super(children);
    }

    public String[] params() {
        ASTree ast = child(0);
        List<String> params = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            params.add(((ASTLeaf) ast.child(i)).token().literal());
        }
        return params.toArray(new String[params.size()]);
    }

    public ASTree body() {
        return child(1);
    }

    @Override
    public Rock eval(Environment env, Rock base) {
        RockFunction func = new RockFunction(null, params(), body(), env);
        //Logger.log("get fun " + func);
        return func;
    }
}
