package rock.data;

import rock.exception.RockException;
import rock.util.Logger;

import java.util.Arrays;

public class Function extends Rock {

    private String name;
    private String[] params;
    private Evaluator body;
    private Environment env;

    public Function(String name, String[] params, Evaluator body, Environment env) {
        this.name = name;
        this.params = params;
        this.body = body;
        this.env = env;
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






    @Override
    public Rock invoke(Rock... args) throws RockException {
        Logger.log("receive args(" + args.length + "): " + Arrays.asList(args));
        if (args.length != params.length) {
            throw new RockException("wrong number of args: expect " + params.length + " get " + args.length);
        }
        Environment newEnv = new Environment(this.env);
        for (int i = 0; i < params.length; i++) {
            newEnv.set(params[i], args[i]);
        }
        return body.eval(newEnv);
    }

    @Override
    public Environment env() throws RockException {
        return env;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer().append(name == null ? "<fun>" : name).append("(");
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
            if (i + 1 < params.length) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }
}
