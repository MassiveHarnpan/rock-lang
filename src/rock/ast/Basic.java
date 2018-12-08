package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.token.Token;

public class Basic extends ASTLeaf {
    public Basic(Token token) {
        super(token);
    }
}
