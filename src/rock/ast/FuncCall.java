package rock.ast;

import rock.Environment;
import rock.Function;
import rock.RockException;

import java.util.ArrayList;
import java.util.List;

public class FuncCall extends ASTList {

    public FuncCall(ASTree... children) {
        super(children);
    }


    public ASTree func() {
        return child(0);
    }

    public List<ASTree> args() {
        ASTree ast = child(1);
        List<ASTree> list = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            list.add(ast.child(i));
        }
        return list;
    }




    @Override
    public Object eval(Environment env) throws RockException {
        Function func = (Function) func().eval(env);
        List<ASTree> list = args();
        Object[] args = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            args[i] = list.get(i).eval(env);
        }
        return func.invoke(env, args);
    }
}
