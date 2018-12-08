package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.token.Token;

public class ASTLeaf extends ASTree {
    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public ASTree child(int i) {
        return null;
    }

    @Override
    public int childCount() {
        return 0;
    }

    @Override
    public Object eval(Environment env) throws RockException {
        return token.value();
    }

    @Override
    public Object eval(Environment env, Object base) throws RockException {
        return eval(env);
    }

    private Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    protected Token token() {
        return token;
    }

    @Override
    public String toString() {
        return token.literal().replace("\n", "#EOL");
    }
}
