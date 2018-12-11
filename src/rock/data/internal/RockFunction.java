package rock.data.internal;

import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.util.Logger;

import java.util.Arrays;

public class RockFunction extends RockAdapter {

    private String name;
    private String[] params;
    private Evaluator body;
    private Environment env;

    public RockFunction(String name, String[] params, Evaluator body, Environment env) {
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
    public RockType type() {
        return RockType.FUN;
    }

    @Override
    public Rock invoke(Rock... args) throws RockException {
        Logger.log("receive args(" + args.length + "): " + Arrays.asList(args));
        if (args.length != params.length) {
            throw new RockException("wrong number of args: expect " + params.length + " get " + args.length);
        }
        NestedEnvironment newEnv = new NestedEnvironment(this.env);
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
        StringBuffer sb = new StringBuffer().append("<function:");
        sb.append(name == null ? "~" : name).append("(");
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
            if (i + 1 < params.length) {
                sb.append(", ");
            }
        }
        return sb.append(")").append(">").toString();
    }
}
