package rock.ast;

import rock.data.*;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.token.Token;
import rock.util.Logger;

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
        Logger.log(value + " instanceof "+value.getClass().getSimpleName());
        if (value instanceof Integer) {
            return new RockInteger((Integer) value);
        } else if (value instanceof Double) {
            return new RockDecimal((Double) value);
        } else if (value instanceof String) {
            return new RockString((String) value);
        } else {
            return null;
        }
    }

    @Override
    public Proxy proxy(Environment env, Rock base) throws RockException {
        return new InstanceProxy(eval(env));
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
