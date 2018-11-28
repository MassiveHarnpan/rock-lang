package rock;

import rock.ast.ASTree;

import java.util.Arrays;

public class Function {

    private String name;
    private String[] params;
    private ASTree body;

    public Function(String name, String[] params, ASTree body) {
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

    public ASTree body() {
        return body;
    }






    public Object eval(Environment env, Object... args) throws RockException {
        Environment lower = env.lower();
        for (int i = 0; i < params.length; i++) {
            lower.put(params[i], i >= args.length ? null : args[i]);
        }
        return body.eval(lower);
    }

}
