package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.IndentationPrinter;
import rock.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FuncDef extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new FuncDef(elements);

    public FuncDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return child(0).token().literal();
    }

    public List<String> params() {
        ASTree ast = child(1);
        List<String> params = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            params.add(ast.child(i).token().literal());
        }
        return params;
    }

    public ASTree body() {
        return child(2);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        String name = name();
        List<String> params = params();
        NestedEnvironment environment = new NestedEnvironment(env);
        environment.set("__function_name__", new RockString(name));
        RockFunction func = new RockFunction(name, params.toArray(new String[params.size()]), body(), environment);
        Logger.log("put " + func);
        return env.set(name, func);
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print("def ").print(name()).print("(");
        ASTList params = (ASTList) child(1);
        Iterator<ASTree> itr = params.iterator();
        while (itr.hasNext()) {
            itr.next().write(printer);
            if (itr.hasNext()) {
                printer.print(", ");
            }
        }
        printer.print(") ");
        body().write(printer);
    }
}
