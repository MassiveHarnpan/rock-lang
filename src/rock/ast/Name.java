package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.RockName;
import rock.exception.RockException;
import rock.token.Token;

public class Name extends ASTLeaf {

    public Name(Token token) {
        super(token);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        return new RockName(env, token().literal());
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        return new RockName(base, token().literal());
    }
}
