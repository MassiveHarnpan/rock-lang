package rock.ast;

import rock.data.EnvProxy;
import rock.data.Environment;
import rock.data.Proxy;
import rock.data.Rock;
import rock.exception.RockException;
import rock.token.Token;
import rock.util.Logger;

public class Name extends ASTLeaf {

    public static final ASTLeafFactory FACTORY = token -> new Name(token);

    public Name(Token token) {
        super(token);
    }

    public String name() {
        return token().literal();
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        String name = name();
        Logger.log("get " + name + " in " + env);
        Rock result = env.get(name);
        if (result == null) {
            throw new RockException("Cannot resolve identifier: " + name);
        }
        return result;
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        return new EnvProxy(base == null ? env : base, token().literal());
    }
}
