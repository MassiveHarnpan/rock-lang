package rock.ast;

import rock.Environment;
import rock.Function;
import rock.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Closure extends ASTList {
    public Closure(ASTree... children) {
        super(children);
    }

    public List<String> params() {
        ASTree ast = child(0);
        List<String> params = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            params.add(((ASTLeaf) ast.child(i)).token().literal());
        }
        return params;
    }

    public ASTree body() {
        return child(1);
    }

    @Override
    public Object eval(Environment env) {
        Function func = new Function(null, params().toArray(new String[params().size()]), body(), env);
        //Logger.log("get fun " + func);
        return func;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("fun ").append("(");
        Iterator<String> itr = params().iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(") ").append(body().toString()).toString();
    }
}
