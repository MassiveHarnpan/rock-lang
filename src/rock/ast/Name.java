package rock.ast;

import rock.Enviroument;
import rock.token.Token;

public class Name extends ASTLeaf {

    public Name(Token token) {
        super(token);
    }

    @Override
    public Object eval(Enviroument env) {
        return env.get(token().literal());
    }
}
