package rock.runtime;

import rock.Environment;
import rock.Function;
import rock.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

public class FuncPrint extends Function {

    public FuncPrint() {
        super("print", new String[] {"msg"}, new ASTList() {
            @Override
            public Object eval(Environment env) throws RockException {
                env.output(String.valueOf(env.get("msg")));
                return null;
            }
        });
    }
}
