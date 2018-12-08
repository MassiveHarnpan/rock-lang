package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.token.Token;

public class Basic extends ASTLeaf {
    public Basic(Token token) {
        super(token);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        return token().value();
    }
}
