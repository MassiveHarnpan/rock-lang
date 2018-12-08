package rock.ast;

import rock.Environment;
import rock.Function;
import rock.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FuncDef extends ASTList {

    public FuncDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }

    public List<String> params() {
        ASTree ast = child(1);
        List<String> params = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            params.add(((ASTLeaf) ast.child(i)).token().literal());
        }
        return params;
    }

    public ASTree body() {
        return child(2);
    }

    @Override
    public Object eval(Environment env) {
        Function func = new Function(name(), params().toArray(new String[params().size()]), body(), env);
        Logger.log("put " + func);
        return env.put(func.name(), func);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("def ").append(name()).append(" (");
        Iterator<String> itr = params().iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ").append(body());
        return sb.toString();
    }
}
