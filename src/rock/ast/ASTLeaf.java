package rock.ast;

import rock.data.*;
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
    public Rock eval(Environment env) throws RockException {
        Object value = token.value();
        if (value instanceof Integer) {
            return new RockInt((Integer) value);
        } else if (value instanceof Double) {
            return new RockDec((Double) value);
        } else if (value instanceof String) {
            return new RockStr((String) value);
        } else {
            return null;
        }
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
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
