package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.token.Token;

public class Name extends ASTLeaf {

    public Name(Token token) {
        super(token);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        return env.get(token().literal());
    }
}
