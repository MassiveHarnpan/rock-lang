package rock;

import rock.ast.ASTree;
import rock.util.Logger;

import java.util.Arrays;

public class Function {

    private String name;
    private String[] params;
    private Evaluator body;

    public Function(String name, String[] params, Evaluator body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public String name() {
        return name;
    }

    public String[] params() {
        return Arrays.copyOf(params, params.length);
    }

    public Evaluator body() {
        return body;
    }






    public Object invoke(Environment env, Object... args) throws RockException {
        Logger.log("receive args(" + args.length + "): " + Arrays.asList(args));
        if (args.length != params.length) {
            throw new RockException("wrong number of args: expect " + params.length + " get " + args.length);
        }
        Environment newEnv = new Environment(env);
        for (int i = 0; i < params.length; i++) {
            newEnv.put(params[i], args[i]);
        }
        return body.eval(newEnv);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer().append(name).append("(");
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
            if (i + 1 < params.length) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }
}
